/**
 * 
 */
package com.thestreet.locationandturfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thestreet.common.dto.LocationsDTO;
import com.thestreet.locationandturfs.model.Location;

/**
 * @author akhil
 *
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
