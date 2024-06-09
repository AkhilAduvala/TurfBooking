/**
 * 
 */
package com.thestreet.authentication.service;

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
	
	public User registerNewUSer(String pasword, String email, String mobile,
			String Fname, String Lname, int groupId) {
		User user = new User();
		
		// encoding the password before saving to the database
		user.setPassword(passwordEncoder.encode(pasword));
		
		user.setEmail(email);
		user.setFname(Fname);
		user.setLname(Lname);
		user.setMobile(mobile);
		
		//fetch and set user groupId
		UserGroup userGroup = userGroupRepository.findById(groupId)
				.orElseThrow(() -> new RuntimeException("User group" + groupId +"not found : " ));
		user.setUserGroup(userGroup);
		
		return userRepository.save(user);
		
	}
}
