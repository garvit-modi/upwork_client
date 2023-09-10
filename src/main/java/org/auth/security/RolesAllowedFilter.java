package org.auth.security;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

// @Provider
// @Priority(Priorities.AUTHORIZATION)
// public class RolesAllowedFilter implements ContainerRequestFilter {

//     @Inject
//     JsonWebToken jwt;

    
//     @Context
//     UriInfo uriInfo;
    
    
//     @Override
//     public void filter(ContainerRequestContext requestContext) {
//           // Get the URL
//           String url = uriInfo.getRequestUri().toString();

//           // Use the URL as needed
//           System.out.println("URL: " + url);

//          if(!url.equals("http://localhost:8111/hello")){
//              // Validate the JWT token
//           if (!jwt.getRawToken().equals("valid_token")) {
//             requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//             return;
//         }

//         // JsonWebToken jwt = requestContext
//         //     .getSecurityContext()
//         //     .getUserPrincipal()
//         //     .cast(JsonWebToken.class);

//         // Perform role-based authorization logic
//         if (!jwt.getGroups().contains("admin") && !jwt.getGroups().contains("user")) {
//             requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
//         }
//          }
//     }
// }