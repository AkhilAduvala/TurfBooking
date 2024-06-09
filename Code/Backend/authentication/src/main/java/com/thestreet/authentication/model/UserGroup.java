/**
 * 
 */
package com.thestreet.authentication.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author akhil
 * 
 * This is a model representation of usergroup
 *
 */

@Entity
@Table(name = "usergroup")
@Data
public class UserGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usergroup_id")
	private int groupId;
	
	@NotNull
	@Column(name = "usergroup_name")
	private String groupName;

	//one to many relationship with user
	@OneToMany(mappedBy = "User", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> users;
}
