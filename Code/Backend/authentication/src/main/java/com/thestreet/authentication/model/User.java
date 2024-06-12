/**
 * 
 */
package com.thestreet.authentication.model;


import java.util.Date;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;

/**
 * @author akhil
 * 
 * This is a model representation of user table
 */

@Entity
@Table(name = "user")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	
	@NotNull
	@Column(name = "user_fname")
	private String fname;
	
	@Column(name = "user_lname")
	private String lname;
	
	@NotNull
	@Column(name = "mobile")
	private String mobile;
	
	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name ="password")
	private String password;
	
	@CreationTimestamp
	@Column(name = "created_on")
	private Date createdOn;
	
	//many to one relationship with usergroup
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usergroupId")
	private UserGroup usergroup;

}
