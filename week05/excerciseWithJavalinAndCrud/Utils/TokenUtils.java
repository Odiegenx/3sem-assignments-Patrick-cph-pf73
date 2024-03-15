package excerciseWithJavalinAndCrud.Utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import excerciseWithJavalinAndCrud.DAO.HotelDAO;
import excerciseWithJavalinAndCrud.DTO.UserDTO;
import excerciseWithJavalinAndCrud.Exception.ApiException;
import excerciseWithJavalinAndCrud.model.Hotel;
import excerciseWithJavalinAndCrud.secrurity.Role;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;

public class TokenUtils {
    /*- and `Utils.createToken` is a method that creates a JWT token from a user
```java*/
    private static byte[] keyBytes;

    // should not be hard coded... but could not get it to work any other way.
    private static String secretKey = "qFjk3Pv2gHs5qDx8rTk7zSv5yBf8gNm7tBc4eMw1uVn3iAw5tKy2rHc6xJe9wXl2oPj4rTk6zSn8yBd5eNg7tBf4gHs7qDx9rVu2";

    private static TokenUtils instance;
    public static TokenUtils getInstance(){
        if(instance == null){
            keyBytes = new byte[32];
            instance = new TokenUtils();
        }
        return instance;
    }
    // below function provided by teacher made minor changes:
    public static String createToken(UserDTO user, String ISSUER, String TOKEN_EXPIRE_TIME, String SECRET_KEY) throws ApiException {
        // https://codecurated.com/blog/introduction-to-jwt-jws-jwe-jwa-jwk/
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer(ISSUER)
                    .claim("username", user.getUsername())
                    .claim("roles", user.getRolesAsString().stream().reduce((s1, s2) -> s1 + "," + s2).get())
                    .expirationTime(new Date(new Date().getTime() + Integer.parseInt(TOKEN_EXPIRE_TIME)))
                    .build();
            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSSigner signer = new MACSigner(SECRET_KEY);
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            jwsObject.sign(signer);
            return jwsObject.serialize();

        } catch (JOSEException e) {
            e.printStackTrace();
            throw new ApiException(500, "Could not create token");
        }
    }
    public static String generateSecretKey(int keyLengthBytes) {
        // Ensure that the key length is at least 256 bits (32 bytes)
        if (keyLengthBytes < 32) {
            throw new IllegalArgumentException("Key length must be at least 256 bits (32 bytes)");
        }

        // Generate random bytes for the secret key
        byte[] randomBytes = keyBytes/*new byte[keyLengthBytes]*/;
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        // Encode the random bytes using Base64 encoding
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    public static UserDTO validateAndExtractUserFromToken(String token) {
        try {
            // Parse the JWT token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Verify the token's signature with the secret key
            JWSVerifier verifier = new MACVerifier(secretKey);
            if (!signedJWT.verify(verifier)) {
                return null; // Signature verification failed
            }

            // Extract JWT claims
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            // Extract user information from claims
            String username = claims.getStringClaim("username");
            String password = ""; // Password won't be present in JWT token
            String roleAsString = extractRolesFromClaims(claims);
            Set<Role> roles = new HashSet<>();
            roles.add(new Role(roleAsString));
            // Create and return UserDTO
            return new UserDTO(username, password, roles);
        } catch (JOSEException | ParseException e) {
            e.printStackTrace(); // TODO add better exception handling
            return null;
        }
    }

    private static String extractRolesFromClaims(JWTClaimsSet claims) throws ParseException {
        return claims.getStringClaim("roles");
    }
}



