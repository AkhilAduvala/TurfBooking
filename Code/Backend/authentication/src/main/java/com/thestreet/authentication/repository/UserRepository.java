/**
 * 
 */
package com.thestreet.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thestreet.authentication.model.User;

/**
 * @author akhil
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	//custom query method to find user by user mobile
	User findByMobile(String mobile);
	
}
