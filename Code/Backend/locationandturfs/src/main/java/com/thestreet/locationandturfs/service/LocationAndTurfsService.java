/**
 * 
 */
package com.thestreet.locationandturfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thestreet.locationandturfs.model.Location;
import com.thestreet.locationandturfs.repository.LocationRepository;

/**
 * @author akhil
 *
 */

@Service
public class LocationAndTurfsService {
	
	@Autowired
	LocationRepository locationRepository;

	public List<Location> getLocations() {
		return locationRepository.findAll();
	}
}
