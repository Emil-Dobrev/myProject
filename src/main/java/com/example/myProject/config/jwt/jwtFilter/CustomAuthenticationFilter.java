package com.example.myProject.config.jwt.jwtFilter;

import com.example.myProject.config.jwt.GenerateTokens;
import com.example.myProject.user.User;
import com.example.myProject.user.UserPrincipal;
import com.example.myProject.user.UserRepository;
import com.example.myProject.user.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final GenerateTokens generateTokens;

    private final UserRepository userRepository;
    private final UserServiceImp userService;


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        if (failed instanceof UserNotVerifiedException) {
//            response.setStatus(PRECONDITION_FAILED.value());
//        }else{
            response.setStatus(FORBIDDEN.value());

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

//        if (request.getHeader("Authorization") != null) {
//            String googleToken = userService.getGoogleToken(request);
//            GoogleIdTokenVerifier verifier = userService.googleVerifier();
//            GoogleIdToken idToken = userService.verifyGoogleIdToken(verifier,googleToken);
//                return userService.processOAuthPostLogin(idToken);
//        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
//        if (!user.getIsVerified()) {
//            throw new UserNotVerifiedException("Please first verify your profile.");
//        }
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        String access_token = generateTokens.createAccessToken(user, request);
        String refresh_token =  generateTokens.createRefreshToken(user, request);

        Map<String, String> tokens = generateTokens.getTokens(access_token,refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

}
