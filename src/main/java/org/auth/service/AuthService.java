/**
 * 
 */
package org.auth.service;

import org.auth.payload.ChangePasswordDto;
import org.auth.payload.JWTAuthResponse;
import org.auth.payload.LoginDto;
import org.auth.payload.RegisterDto;
import org.auth.payload.UserDetailsDto;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Garvit
 *
 */
public interface AuthService {
	
	JWTAuthResponse login (LoginDto loginDto);
	
	String changePassword (ChangePasswordDto changePassDto);
	
	UserDetailsDto getCurrentUserDetails ();
	
	// Uni<String> register (RegisterDto registerDto);
	String register (RegisterDto registerDto);

}
