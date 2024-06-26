/**
 * 
 */
package com.thestreet.locationandturfs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author akhil
 *
 *This is a model representation of location table
 *
 */

@Entity
@Table(name = "locations")
@Data
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "location_id")
	private int locationId;
	
	@Column(name = "location_name")
	private String locationName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "contact_info")
	private String contactInfo;
	

}
