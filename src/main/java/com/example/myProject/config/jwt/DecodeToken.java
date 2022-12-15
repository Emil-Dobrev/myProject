package com.example.myProject.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.myProject.constants.Constants.BEARER;

@AllArgsConstructor
@Component
public class DecodeToken {

    private final JwtAlgorithm jwtAlgorithm;

    public DecodedJWT getDecodedJWT(String authorizationHeader){
        String token = authorizationHeader.substring(BEARER.length());
        JWTVerifier verifier = JWT.require(jwtAlgorithm.getAlgorithm()).build();
        return verifier.verify(token);
    }
}
