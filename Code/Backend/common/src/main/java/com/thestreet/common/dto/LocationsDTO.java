/**
 * 
 */
package com.thestreet.common.dto;

import lombok.Data;
import net.bytebuddy.asm.Advice.Unused;

/**
 * @author akhil
 *
 */

@Data
@SuppressWarnings("unused")
public class LocationsDTO {
	
	private int locationId;
	private String locationName;
	private String address;
	private String contactInfo;

}
