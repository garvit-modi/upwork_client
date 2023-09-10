package org.auth.payload;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordDto {

	private String oldPassword;
	private String newPassword;
	
}
