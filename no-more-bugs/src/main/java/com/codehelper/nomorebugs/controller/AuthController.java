package com.codehelper.nomorebugs.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codehelper.nomorebugs.entity.Role;
import com.codehelper.nomorebugs.entity.User;
import com.codehelper.nomorebugs.exception.ApiException;
import com.codehelper.nomorebugs.payload.JWTAuthResponse;
import com.codehelper.nomorebugs.payload.LoginDto;
import com.codehelper.nomorebugs.payload.SignupDto;
import com.codehelper.nomorebugs.repository.RoleRepository;
import com.codehelper.nomorebugs.repository.UserRepository;
import com.codehelper.nomorebugs.security.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Auth controller exposes signin and signup REST APIs which are used for login and registering user.")
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@ApiOperation(value = "REST API to login into the application using email address and password")
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> authenticateUser(@Valid @RequestBody LoginDto loginDto) {

		// get the user
		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// get the token from token provider class
		String token = jwtTokenProvider.generateToken(authentication);


		return new ResponseEntity<JWTAuthResponse>(new JWTAuthResponse(token, user.getId()), HttpStatus.OK);
	}

	@ApiOperation(value = "REST API to register into the application")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signupDto) {
		// check if user already exist
		if (userRepository.existsByEmail(signupDto.getEmail())) {
			return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
		}

		// create user object
		User user = new User();
		user.setEmail(signupDto.getEmail());
		user.setFirstName(signupDto.getFirstName());
		user.setLastName(signupDto.getLastName());
		user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

		Role roles = roleRepository.findByName("ROLE_USER").get();
		user.setRoles(Collections.singleton(roles));

		userRepository.save(user);

		return new ResponseEntity<>("User signedup successfully", HttpStatus.OK);
	}
}
