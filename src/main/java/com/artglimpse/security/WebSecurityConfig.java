package com.artglimpse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;  
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;  
import org.springframework.security.config.annotation.web.builders.HttpSecurity;  
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;  
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;  
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  
@EnableWebSecurity  
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean  
    public JwtAuthenticationFilter jwtAuthenticationFilter() {  
        return new JwtAuthenticationFilter();  
    }

    @Bean  
    public PasswordEncoder passwordEncoder() {  
        return new BCryptPasswordEncoder();  
    }

    @Bean  
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {  
        return http.getSharedObject(AuthenticationManagerBuilder.class)  
                   .userDetailsService(customUserDetailsService)  
                   .passwordEncoder(passwordEncoder)  
                   .and().build();  
    }

    @Bean  
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {  
        http  
            .csrf().disable()  
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  
            .and()  
            .authorizeRequests()  
                .antMatchers("/api/auth/**").permitAll()  
                .anyRequest().authenticated();  

        // Add JWT filter  
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);  

        return http.build();  
    }
}
