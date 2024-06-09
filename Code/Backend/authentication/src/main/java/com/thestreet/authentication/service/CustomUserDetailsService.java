/**
 * 
 */
package com.thestreet.authentication.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thestreet.authentication.model.User;
import com.thestreet.authentication.repository.UserRepository;

/**
 * @author akhil
 * 
 * CustomUserDetailsService implements UserDetailsService to load user-specific data.
 * Used by spring security during authentication.
 *
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		logger.info("Entering the CustomUserDetailsService to find the user from the provided mobile number");
		
		User user = userRepository.findByMobile(identifier);

		if (user == null) {
			throw new UsernameNotFoundException("User not found : " + identifier);
		}
		
		UserBuilder builder = withUsername(user.getMobile());
		builder.password(user.getPassword());
		builder.roles(user.getUserGroup().getGroupName());
			
		return builder.build();
	}

}
