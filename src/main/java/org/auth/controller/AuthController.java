/**
 *
 */
package org.auth.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.auth.payload.ChangePasswordDto;
import org.auth.payload.LoginDto;
import org.auth.payload.RegisterDto;
import org.auth.payload.SuccessReponse;
import org.auth.service.AuthService;
import org.jboss.logging.Logger;


/**
 * Controller for authentication-related operations.
 * 
 * @author Garvit
 */
@Path("/api/")
public class AuthController {

  @Inject
  Logger logger;

  @Inject
  AuthService authService;

  /**
   * Endpoint to create a new user.
   * 
   * @param registerDto Registration data for the new user.
   * @return HTTP response with a status of 201 (Created) and a success response entity.
   */
  @POST
  @Path(value = "auth/register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response register(RegisterDto registerDto) {
    logger.info("Register API call");
    return Response
      .status(Response.Status.CREATED)
      .entity(new SuccessReponse(authService.register(registerDto)))
      .build();
  }

  /**
   * Endpoint for user login.
   * 
   * @param loginDto Login credentials.
   * @return HTTP response with a status of 200 (OK) and a login response entity.
   */
  @POST
  @Path(value = "auth/login")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(LoginDto loginDto) {
    logger.info("Login API call");
    return Response
      .status(Response.Status.OK)
      .entity(authService.login(loginDto))
      .build();
  }

  /**
   * Endpoint to change a user's password.
   * 
   * @param changePasswordDto Change password data.
   * @return HTTP response with a status of 204 (No Content) and a success response entity.
   */
  @POST
  @Path(value = "changePassword")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed({"ADMIN", "USER"})
  public Response changePassword(ChangePasswordDto changePasswordDto) {
    logger.info("Change Password API call");
    return Response
      .status(Response.Status.NO_CONTENT)
      .entity(new SuccessReponse(authService.changePassword(changePasswordDto)))
      .build();
  }

  /**
   * Endpoint to retrieve user details.
   * 
   * @return HTTP response with a status of 200 (OK) and user details entity.
   */
  @GET
  @Path(value = "userDetails")
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({"ADMIN", "USER"})
  public Response userDetails() {
    logger.info("User Details API call");
    return Response
      .status(Response.Status.OK)
      .entity(authService.getCurrentUserDetails())
      .build();
  }
}
