package com.artglimpse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class UnifiedSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public UnifiedSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(auth -> auth
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/products/**").permitAll()
                .antMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/wishlist/**").permitAll()
                .antMatchers(HttpMethod.POST, "/wishlist/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/wishlist/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/wishlist/**").permitAll()
                .antMatchers(HttpMethod.GET, "/cart/**").permitAll()
                .antMatchers(HttpMethod.POST, "/cart/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/cart/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/cart/**").permitAll()
                // Allow GET requests to ordersList for all, but require authentication for PATCH (update)
                .antMatchers(HttpMethod.GET, "/api/ordersList/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/ordersList/**").permitAll()
                // Other ordersList endpoints as needed:
                .antMatchers(HttpMethod.POST, "/ordersList/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/ordersList/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/ordersList/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/chat").permitAll()
                .antMatchers(HttpMethod.POST, "/api/chat").permitAll()
                .antMatchers(HttpMethod.POST, "/api/seller/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/stats").permitAll()
                .antMatchers("/seller/**").hasRole("SELLER")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:5174", "http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
