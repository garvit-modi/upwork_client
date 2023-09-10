package org.auth.exception;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Date;
import  org.jboss.logging.Logger;
import org.auth.payload.ErrorDetails;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
// @RequestScoped
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class);    

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    JsonWebToken jwt;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable throwable) {
        LOGGER.error("An error occurred", throwable);

        if (throwable instanceof UnauthorizedUserException) {
            return handleUnauthorizedUserException(
                    (UnauthorizedUserException) throwable);
        }
        else if (throwable instanceof ApiException) {
            return handleApiException(
                    (ApiException) throwable);
        }
        //  else if (throwable instanceof FileNotFoundException) {
        //     return handleFileNotFoundException((FileNotFoundException) throwable);
        // } else if (throwable instanceof FileStorageException) {
        //     return handleFileStorageException((FileStorageException) throwable);
        // } else if (throwable instanceof MaxUploadSizeExceededException) {
        //     return handleMaxSizeException((MaxUploadSizeExceededException) throwable);
        // } else if (throwable instanceof MethodArgumentNotValidException) {
        //     return handleMethodArgumentNotValidException(
        //             (MethodArgumentNotValidException) throwable);
        // } else if (throwable instanceof AccessDeniedException) {
        //     return handleAccessDeniedException((AccessDeniedException) throwable);
        // }
        // else  if (throwable instanceof ResourceNotFoundException) {
        //     return handleResourceNotFoundException(
        //             (ResourceNotFoundException) throwable);
        // }
         else if (throwable instanceof Exception) {
            return handleGlobalException((Exception) throwable);
        } 

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                throwable.getMessage(),
                uriInfo.getPath());

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorDetails)
                .build();
    }

   

    private Response handleUnauthorizedUserException(
            UnauthorizedUserException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                uriInfo.getPath());
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(errorDetails)
                .build();
    }

    private Response handleApiException(
            ApiException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                uriInfo.getPath());
        return Response
                .status(exception.getStatus())
                .entity(errorDetails)
                .build();
    }

//     private Response handleFileNotFoundException(
//             FileNotFoundException exception) {
//         ErrorDetails errorDetails = new ErrorDetails(
//                 new Date(),
//                 exception.getMessage(),
//                 uriInfo.getPath());
//         return Response
//                 .status(Response.Status.NOT_FOUND)
//                 .entity(errorDetails)
//                 .build();
//     }

//     private Response handleResourceNotFoundException(
//         ResourceNotFoundException exception) {
//     ErrorDetails errorDetails = new ErrorDetails(
//             new Date(),
//             exception.getMessage(),
//             uriInfo.getPath());
//     return Response
//             .status(Response.Status.NOT_FOUND)
//             .entity(errorDetails)
//             .build();
// }

// private Response handleBlogAPIException(BlogAPIException exception) {
//     ErrorDetails errorDetails = new ErrorDetails(
//             new Date(),
//             exception.getMessage(),
//             uriInfo.getPath());
//     return Response
//             .status(Response.Status.BAD_REQUEST)
//             .entity(errorDetails)
//             .build();
// }

//     private Response handleFileStorageException(FileStorageException exception) {
//         ErrorDetails errorDetails = new ErrorDetails(
//                 new Date(),
//                 exception.getMessage(),
//                 uriInfo.getPath());
//         return Response
//                 .status(Response.Status.NOT_FOUND)
//                 .entity(errorDetails)
//                 .build();
//     }

//     private Response handleMaxSizeException(
//             MaxUploadSizeExceededException exception) {
//         ErrorDetails errorDetails = new ErrorDetails(
//                 new Date(),
//                 exception.getMessage(),
//                 uriInfo.getPath());
//         return Response
//                 .status(Response.Status.EXPECTATION_FAILED)
//                 .entity(errorDetails)
//                 .build();
//     }

    private Response handleGlobalException(
            Exception exception) {
                ErrorDetails errorDetails = new ErrorDetails(
                                    new Date(),
                                    exception.getMessage(),
                                    uriInfo.getPath());
                            return Response
                                    .status(Response.Status.EXPECTATION_FAILED)
                                    .entity(errorDetails)
                                       .build();

    }
}
