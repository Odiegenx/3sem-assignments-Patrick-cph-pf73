package excerciseWithJavalinAndCrud.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import excerciseWithJavalinAndCrud.DAO.UserDAO;
import excerciseWithJavalinAndCrud.DTO.UserDTO;
import excerciseWithJavalinAndCrud.Exception.ApiException;
import excerciseWithJavalinAndCrud.Utils.TokenUtils;
import excerciseWithJavalinAndCrud.secrurity.User;
import io.javalin.http.Context;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SecurityController  {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static String secretKey = "qFjk3Pv2gHs5qDx8rTk7zSv5yBf8gNm7tBc4eMw1uVn3iAw5tKy2rHc6xJe9wXl2oPj4rTk6zSn8yBd5eNg7tBf4gHs7qDx9rVu2";

    public static String createToken(UserDTO user) throws ApiException {
        String ISSUER;
        String TOKEN_EXPIRE_TIME;
        String SECRET_KEY;

        if (System.getenv("DEPLOYED") != null) {
            ISSUER = System.getenv("ISSUER");
            TOKEN_EXPIRE_TIME = System.getenv("TOKEN_EXPIRE_TIME");
            SECRET_KEY = System.getenv("SECRET_KEY");
        } else {
            ISSUER = "Patrick";
            TOKEN_EXPIRE_TIME = "1800000"; // 30 minutes in milliseconds
            SECRET_KEY = secretKey; /*TokenUtils.generateSecretKey(32);*//*Utils.getPropertyValue("SECRET_KEY","config.properties");*/
        }
        return TokenUtils.createToken(user, ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY);
    }

    public static void authenticate(Context ctx) {
                // To check the users roles against the allowed roles for the endpoint (managed by javalins accessManager)
                // Checked in 'before filter' -> Check for Authorization header to find token.
                // Find user inside the token, forward the ctx object with userDTO on attribute
                // When ctx hits the endpoint it will have the user on the attribute to check for roles (ApplicationConfig -> accessManager)
                ObjectNode returnObject = objectMapper.createObjectNode();
                    if(ctx.method().toString().equals("OPTIONS")) {
                        ctx.status(200);
                        return;
                    }
                    String header = ctx.header("Authorization");
                    if (header == null) {
                        ctx.status(403).json(returnObject.put("msg", "Authorization header missing"));
                        return;
                    }
                    String token = header.split(" ")[1];
                    if (token == null) {
                        ctx.status(403).json(returnObject.put("msg", "Authorization header malformed"));
                        return;
                    }
                    UserDTO verifiedTokenUser = TokenUtils.validateAndExtractUserFromToken(token);
                    if (verifiedTokenUser == null) {
                        ctx.status(403).json(returnObject.put("msg", "Invalid User or Token"));
                    }
                    System.out.println("USER IN AUTHENTICATE: " + verifiedTokenUser);
                    ctx.attribute("user", verifiedTokenUser);
                };

    public static boolean authorize(UserDTO user, Set<String> allowedRoles) {
        AtomicBoolean hasAcces = new AtomicBoolean(false);
        if(user != null){
            user.getRoles().forEach(role ->{
                if(allowedRoles.contains(role.getName().toUpperCase())){
                    hasAcces.set(true);
                }
            });
        }
        return hasAcces.get();
    }

    private static int timeToExpire(String token) throws ParseException {
        SignedJWT jwt = SignedJWT.parse(token);
        return (int) (jwt.getJWTClaimsSet().getExpirationTime().getTime() - new Date().getTime());
    }

    public static boolean tokenIsValid(String token, String secret) throws ParseException, KeyLengthException {
        JWTClaimsSet claimsSet = JWTClaimsSet.parse(token);
        return true;
    }

    public static UserDTO getUserWithRolesFromToken(String token) {
        try {
            JWTClaimsSet claims = JWTClaimsSet.parse(token);
            String user = claims.getStringClaim("user");
            String pass = claims.getStringClaim("password");
            User userEntity = UserDAO.getInstance(false).getById(user);
            UserDTO dto = null;
            if(userEntity != null) {
                dto = new UserDTO(userEntity);
            }
            return dto;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
