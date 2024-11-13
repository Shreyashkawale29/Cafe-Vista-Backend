package com.cafe.configuration;

import com.cafe.service.auth.jwt.UserService;
import com.cafe.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // This is important to make this class a Spring-managed bean
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Check if the Authorization header is valid and starts with "Bearer "
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is missing or does not start with 'Bearer '");
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the header
        jwt = authHeader.substring(7);

        // Log the extracted JWT for debugging
        logger.debug("Extracted JWT Token: " + jwt);

        // NEW CHECK: Ensure the JWT is not an email or invalid value
        if (jwt.contains("@")) {
            logger.error("Authorization header contains an email instead of a JWT token: " + jwt);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token format");
            return;
        }

        // Check if the JWT token contains three parts
        if (jwt.split("\\.").length != 3) {
            logger.error("Malformed JWT Token: " + jwt);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token format");
            return;
        }

        // Extract the username (email) from the JWT
        userEmail = jwtUtil.extractUserName(jwt);

        logger.debug("Extracted User Email from JWT: " + userEmail);

        if (StringUtils.hasText(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            if (userDetails == null) {
                logger.error("UserDetails could not be loaded for email: " + userEmail);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }

            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Authentication successful for user: " + userEmail);
            } else {
                logger.warn("JWT token is invalid for user: " + userEmail);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

}
