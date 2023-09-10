package org.auth.service.impl;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;
import org.auth.entity.ROLES;
import org.auth.entity.Role;
import org.auth.entity.Users;
import org.auth.exception.ApiException;
import org.auth.exception.UnauthorizedUserException;
import org.auth.payload.ChangePasswordDto;
import org.auth.payload.JWTAuthResponse;
import org.auth.payload.LoginDto;
import org.auth.payload.RegisterDto;
import org.auth.payload.UserDetailsDto;
import org.auth.repository.RoleRepository;
import org.auth.repository.UserRepository;
import org.auth.service.AuthService;
import org.auth.service.TokenService;
import org.jboss.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Implementation of the AuthService interface for authentication-related operations.
 * This service provides user registration, login, password change, and user details retrieval.
 * 
 * @author Garvit
 */
@ApplicationScoped
public class AuthServiceImpl implements AuthService {

  @Inject
  Logger LOG;

  @Inject
  TokenService tokenService;

  @Inject
  private RoleRepository roleRepository;

  @Inject
  private UserRepository userRepository;

  @Inject
  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Inject
  SecurityIdentity securityIdentity;

  /**
   * Registers a new user.
   * 
   * @param registerDto Registration data for the new user.
   * @return A message indicating successful registration.
   */
  @Override
  @Transactional
  public String register(RegisterDto registerDto) {
    LOG.info("Register service call");

    // Check if the username and email already exist in the database
    Users existingUserByUsername = userRepository.findByUsername(registerDto.getUsername());
    Users existingUserByEmail = userRepository.findByEmail(registerDto.getEmail());

    if (existingUserByUsername != null) {
      LOG.error("Username is already exists!.");
      throw new ApiException(Response.Status.BAD_REQUEST, "Username is already exists!.");
    }

    if (existingUserByEmail != null) {
      LOG.error("Email is already exists!.");
      throw new ApiException(Response.Status.BAD_REQUEST, "Email is already exists!.");
    }

    Users user = new Users();
    user.setName(registerDto.getName());
    user.setUsername(registerDto.getUsername());
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

    Set<Role> roles = new HashSet<>();
    Role userRole = roleRepository.findByName(ROLES.USER.toString());
    roles.add(userRole);
    user.setRoles(roles);
    userRepository.persist(user);

    if (userRepository.isPersistent(user)) {
      LOG.info("User registered successfully");
      return "User registered successfully";
    } else {
      LOG.error("Internal Server Error saving user");
      throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, "Internal Server Error!");
    }
  }

  /**
   * Logs in a user.
   * 
   * @param loginDto Login credentials.
   * @return A JWTAuthResponse containing an access token.
   */
  @Override
  public JWTAuthResponse login(LoginDto loginDto) {
    LOG.info("Login service call");

    Users user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail())
      .orElseThrow(() -> new UnauthorizedUserException("User does not exist!."));

    if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      throw new UnauthorizedUserException("Password does not match");
    }

    UserDetailsDto userDetailsDto = UserDetailsDto.builder()
      .name(user.getName())
      .email(user.getEmail())
      .roles(user.getRoles())
      .profileImage(user.getProfileImage())
      .username(user.getUsername())
      .build();

    JWTAuthResponse authResponse = JWTAuthResponse.builder()
      .tokenType("Bearer")
      .userDetailsDto(userDetailsDto)
      .accessToken(tokenService.genratedToken(user.getUsername(), ROLES.USER.toString()))
      .build();

    return authResponse;
  }

  /**
   * Changes a user's password.
   * 
   * @param changePassDto Password change data.
   * @return A message indicating successful password change.
   */
  @Override
  public String changePassword(ChangePasswordDto changePassDto) {
    LOG.info("Change Password service call");
    String username = securityIdentity.getPrincipal().getName();
    Users user = userRepository.findByUsernameOrEmail(username)
      .orElseThrow(() -> new UnauthorizedUserException("User does not exist!."));

    if (!passwordEncoder.matches(changePassDto.getOldPassword(), user.getPassword())) {
      throw new UnauthorizedUserException("Password does not match");
    }

    user.setPassword(passwordEncoder.encode(changePassDto.getNewPassword()));
    userRepository.persist(user);

    return "Change Password Successful";
  }

  /**
   * Retrieves the details of the currently authenticated user.
   * 
   * @return UserDetailsDto containing the user's details.
   */
  @Override
  public UserDetailsDto getCurrentUserDetails() {
    String username = securityIdentity.getPrincipal().getName();
    LOG.info("Username : " + username);
    Users user = userRepository.findByUsernameOrEmail(username)
      .orElseThrow(() -> new UnauthorizedUserException("User does not exist!."));

    UserDetailsDto userDetailsDto = UserDetailsDto.builder()
      .name(user.getName())
      .email(user.getEmail())
      .roles(user.getRoles())
      .profileImage(user.getProfileImage())
      .username(user.getUsername())
      .build();

    return userDetailsDto;
  }
}
