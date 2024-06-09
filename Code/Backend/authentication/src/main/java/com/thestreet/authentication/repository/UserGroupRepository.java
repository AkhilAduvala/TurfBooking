/**
 * 
 */
package com.thestreet.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thestreet.authentication.model.UserGroup;

/**
 * @author akhil
 *
 */
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

}
