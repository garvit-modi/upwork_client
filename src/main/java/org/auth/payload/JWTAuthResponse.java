package org.auth.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTAuthResponse {
	
	private String accessToken;
	private String tokenType = "Bearer";
	
	private UserDetailsDto userDetailsDto;
	
	

}
