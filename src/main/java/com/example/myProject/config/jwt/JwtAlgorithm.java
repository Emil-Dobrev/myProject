package com.example.myProject.config.jwt;


import com.auth0.jwt.algorithms.Algorithm;
import com.example.myProject.config.JwtSecret;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JwtAlgorithm {

    private final JwtSecret jwtSecret;

     public Algorithm getAlgorithm(){
         return Algorithm.HMAC256(jwtSecret.secret.getBytes());
     }

}
