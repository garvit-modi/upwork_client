package org.auth.service;

import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.smallrye.jwt.build.JwtSignature;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.auth.entity.Role;
import org.auth.exception.ApiException;

@Singleton
public class TokenService {

  public  String genratedToken(String username, String role) {
    Set<String> roles = new HashSet<>(Arrays.asList(role));
    try {

      return generateToken(username, roles);
    } catch (Exception e) {
      throw new ApiException(
        Response.Status.INTERNAL_SERVER_ERROR,
        "Server down " + e.getMessage()
      );
    }
  }

  public  String generateToken(String username, Set<String> roles)
    throws Exception {
    JwtClaimsBuilder claimsBuilder = Jwt.claims();
    long currentTimeInSecs = currentTimeInSecs();

    claimsBuilder.issuer("jwt-token");
    claimsBuilder.subject(username);
    claimsBuilder.issuedAt(currentTimeInSecs);
    claimsBuilder.expiresIn(Duration.of(1, ChronoUnit.HOURS));
    claimsBuilder.groups(roles);
    return claimsBuilder.sign();
  
  }


	public static int currentTimeInSecs() {
		long currentTimeMS = System.currentTimeMillis();
		return (int) (currentTimeMS / 1000);
	}


}
