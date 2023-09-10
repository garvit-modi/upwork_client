package org.auth.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Garvit
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorDetails {
	
	private Date timestamp;
	private String message;
	private String details;
	
	
	

}
