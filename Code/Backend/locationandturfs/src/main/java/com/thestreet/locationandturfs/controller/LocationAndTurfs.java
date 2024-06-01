/**
 * 
 */
package com.thestreet.locationandturfs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thestreet.locationandturfs.model.Location;
import com.thestreet.locationandturfs.service.LocationAndTurfsService;

/**
 * @author akhil 
 * 
 * This module is used for handling all the locations and turfs 
 *
 */
@RestController
@RequestMapping("api/locationandturfs")
public class LocationAndTurfs {
	
	@Autowired
	private LocationAndTurfsService locationAndTurfsService;
	
	/**
	 * 
	 * getLocations() is to fetch all the available locations
	 * no parameters
	 * returns the list of locations
	 * 
	 * */
	@GetMapping("/get-location")
	public ResponseEntity<List<Location>> getLocations(){
		List<Location> locations = locationAndTurfsService.getLocations();
		return ResponseEntity.ok(locations);
	}

}
