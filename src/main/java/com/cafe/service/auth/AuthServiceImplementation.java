package com.cafe.service.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cafe.dto.SignupRequest;
import com.cafe.dto.UserDto;
import com.cafe.entity.User;
import com.cafe.enums.UserRole;
import com.cafe.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthServiceImplementation implements AuthService {

	private final UserRepository userrepository;
	
	
	
	
	public AuthServiceImplementation(UserRepository userrepository) {
		super();
		this.userrepository = userrepository;
		
	}

	@PostConstruct
	public void createAdminAccount() {
		Optional<User> optionalAdminAccount = userrepository.findByUserRole(UserRole.ADMIN);

		// Fix: If no admin exists, create one
		if (optionalAdminAccount.isEmpty()) {  // Check if admin is not present
			User user = new User();
			user.setName("admin");
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setUserRole(UserRole.ADMIN);
			userrepository.save(user);
		}
	}




	@Override
	public UserDto createUser(SignupRequest signupRequest) {


		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword( new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		
		User createUser = userrepository.save(user);
		
		UserDto createUserDto = new UserDto();
		createUserDto.setId(createUser.getId());
		createUserDto.setEmail(createUser.getEmail());
		createUserDto.setUserRole(createUser.getUserRole());
		
		return createUserDto;
	}

	

	
}
