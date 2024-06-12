/**
 * 
 */
package com.thestreet.authentication.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thestreet.authentication.model.User;
import com.thestreet.authentication.model.UserGroup;
import com.thestreet.authentication.repository.UserGroupRepository;
import com.thestreet.authentication.repository.UserRepository;

/**
 * @author akhil
 * 
 * UserService provides the business logic for user-related operations
 *
 */

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	public User registerNewUser(String password, String email, String mobile,
			String Fname, String Lname, int groupId) {
		logger.info("Entering registerNewUser in service");
		User user = new User();
		
		// encoding the password before saving to the database
		user.setPassword(passwordEncoder.encode(password));
		
		user.setEmail(email);
		user.setFname(Fname);
		user.setLname(Lname);
		user.setMobile(mobile);
		
		//fetch and set user groupId
		UserGroup userGroup = userGroupRepository.findById(groupId)
				.orElseThrow(() -> new RuntimeException("User group" + groupId +"not found : " ));
		user.setUsergroup(userGroup);
		logger.info("Exiting the registerNewUser after saving the user details");

		return userRepository.save(user);
		
	}
}
