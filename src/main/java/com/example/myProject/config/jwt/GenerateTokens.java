package com.example.myProject.config.jwt;

import com.auth0.jwt.JWT;
import com.example.myProject.user.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import static com.example.myProject.constants.Constants.*;


@Component
@AllArgsConstructor
public class GenerateTokens {

    private final JwtAlgorithm jwtAlgorithm;

    public String createAccessToken(UserPrincipal user, HttpServletRequest request){
        return JWT.create()
                .withSubject(user.getId())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION_TIME)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(jwtAlgorithm.getAlgorithm());
    }

    public String createRefreshToken(UserPrincipal user, HttpServletRequest request){
        return JWT.create()
                .withSubject(user.getId())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(REFRESH_TOKEN_EXPIRATION_TIME)))
                .withIssuer(request.getRequestURL().toString())
                .sign(jwtAlgorithm.getAlgorithm());
    }

    public HashMap<String, String> getTokens(String accessToken , String refreshToken){
        HashMap<String, String> tokens =  new HashMap<>() ;
        tokens.put(ACCSESS_TOKEN, accessToken);
        tokens.put(REFRESH_TOKEN, refreshToken);
        return tokens;
    }
}
