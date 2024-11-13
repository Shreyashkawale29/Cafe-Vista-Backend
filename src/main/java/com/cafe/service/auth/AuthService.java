package com.cafe.service.auth;

import org.springframework.http.ResponseEntity;

import com.cafe.dto.SignupRequest;
import com.cafe.dto.UserDto;

public interface AuthService {

	UserDto createUser(SignupRequest signupRequest);

}
