package com.thestreet.authentication.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thestreet.authentication.model.User;
import com.thestreet.authentication.security.JwtUtil;
import com.thestreet.authentication.service.CustomUserDetailsService;
import com.thestreet.authentication.service.UserService;

/**
 * @author akhil
 * 
 * SecurityConfiguration sets up the security configurations, 
 * including authentication and authorization rules
 *
 */

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	private static final Logger logger = LogManager.getLogger(AuthenticationController.class);
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String, String> request) 
			throws Exception{
		logger.info("Entering the login user in authentication controller");
		String identifier = request.get("identifier");
		String password = request.get("password");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, password));
		} catch (AuthenticationException e) {
			// TODO: handle exception
			throw new Exception("incorrect username or password", e);
		}
		
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(identifier);
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("token", jwt));
	}
	
	 @PostMapping("/register")
	    public ResponseEntity<?> registerUser(@RequestBody User user) {
		 	System.out.println("recieved the register call");
		 	logger.info("Entering the register user in authentication controller");
	        User newUser = userService.registerNewUser(
	            user.getPassword(), 
	            user.getEmail(), 
	            user.getMobile(), 
	            user.getFname(), 
	            user.getLname(), 
	            user.getUsergroup().getGroupId()
	        );
	        return ResponseEntity.ok(newUser.getFname() + " registered successfully as user.");
	    }

}
