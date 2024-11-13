package com.cafe.configuration;

import com.cafe.enums.UserRole;
import com.cafe.service.auth.jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UserService userService;

	public WebSecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userService = userService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF
				.authorizeHttpRequests(request -> request
						.requestMatchers("/api/auth/**").permitAll()  // Allow unauthenticated access to authentication endpoints
						.requestMatchers("/api/admin/categories").permitAll()  // Allow unauthenticated access to categories
						.requestMatchers("/api/admin/category").permitAll()
						.requestMatchers("/api/admin/{categoryId}/product").permitAll()
						.requestMatchers("/api/admin/{categoryId}/products").permitAll()
						.requestMatchers("/api/admin/**").permitAll()
						// Admin-specific endpoints require authorization
						.anyRequest().authenticated())  // All other requests require authentication
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Use stateless sessions
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService.userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
