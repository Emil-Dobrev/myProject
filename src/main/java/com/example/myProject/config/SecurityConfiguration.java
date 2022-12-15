package com.example.myProject.config;


import com.example.myProject.config.jwt.GenerateTokens;
import com.example.myProject.config.jwt.jwtFilter.CustomAuthenticationFilter;
import com.example.myProject.config.jwt.jwtFilter.JwtAuthFilter;

import com.example.myProject.user.User;
import com.example.myProject.user.UserPrincipal;
import com.example.myProject.user.UserRepository;
import com.example.myProject.user.UserServiceImp;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

import static com.example.myProject.constants.Constants.LOGIN;
import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration  {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserRepository userRepository;
    private final UserDetailsService userDetails;
    private final AuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final GenerateTokens generateTokens;
    private final UserServiceImp userServiceImpl;
    private final CustomAuthenticationFilter customAuthenticationFilter;
    private final CorsConfiguration corsConfiguration;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .cors()
                .configurationSource(request -> corsConfiguration)
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(customAuthenticationFilter, CustomAuthenticationFilter.class)
                .addFilter(jwtAuthFilter)

        ;

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/v1/users/hi");
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider =new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetails);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {;
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
            return new UserPrincipal(user);
        };
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManager, generateTokens, userRepository,userServiceImpl);
        customAuthenticationFilter.setFilterProcessesUrl(LOGIN);
        return customAuthenticationFilter;
    }

    @Bean
    public CorsConfiguration configuration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        var httpMethods = List.of(HttpMethod.DELETE,HttpMethod.POST, HttpMethod.GET, HttpMethod.PATCH);
        httpMethods.forEach(corsConfiguration::addAllowedMethod);
        return corsConfiguration;
    }
}
