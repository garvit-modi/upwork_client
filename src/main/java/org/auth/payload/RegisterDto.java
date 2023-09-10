package org.auth.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {
	private String name ; 
	private String username;
	 private String email;
	 private String password;

}

