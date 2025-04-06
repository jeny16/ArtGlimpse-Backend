
package com.artglimpse.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.artglimpse.authentication.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class UnifiedSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // JWT filter for validating tokens on incoming requests
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // In-memory user details manager (for testing purposes)
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("adminpassword")
                .roles("ADMIN")
                .build();

        UserDetails seller = User.withDefaultPasswordEncoder()
                .username("seller")
                .password("sellerpassword")
                .roles("SELLER")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("userpassword")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, seller, user);
    }

    // Configure AuthenticationManager using your custom user details service and password encoder.
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

    // Global CORS configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow your client origins
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5174", "http://localhost:5173"));
        // Allow necessary HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // Allow all headers (or specify only the ones you need)
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Allow credentials if needed
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this configuration to all endpoints
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Unified security filter chain configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Use the global CORS configuration defined above
                .and()
                .csrf().disable()
                // Use stateless sessions since we're using JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Define endpoint-specific security rules
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/products/**").permitAll()
                .antMatchers(HttpMethod.POST, "/products/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/products/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/products/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/products/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/storage/buckets/{bucketId}/files").permitAll()
                .antMatchers(HttpMethod.GET, "/wishlist/**").permitAll()
                .antMatchers(HttpMethod.POST, "/wishlist/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/wishlist/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/wishlist/**").permitAll()
                .antMatchers(HttpMethod.GET, "/cart/**").permitAll()
                .antMatchers(HttpMethod.POST, "/cart/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/cart/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/cart/**").permitAll()
                .antMatchers(HttpMethod.GET, "/orders/**").permitAll()
                .antMatchers(HttpMethod.POST, "/orders/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/orders/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/orders/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/ordersList/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/ordersList/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/ordersList/**").permitAll()
                .antMatchers(HttpMethod.POST, "/ordersList/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/ordersList/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/ordersList/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/chat").permitAll()
                .antMatchers(HttpMethod.POST, "/api/chat").permitAll()
                .antMatchers(HttpMethod.POST, "/api/seller/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/stats").permitAll()
                .antMatchers(HttpMethod.GET, "/api/seller/profile/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/seller/profile/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/seller/store/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/seller/store/**").permitAll()
                .antMatchers("/seller/**").hasRole("SELLER")
                // Any other request must be authenticated
                .anyRequest().authenticated()
                .and()
                // Optionally enable HTTP Basic authentication for testing or fallback
                .httpBasic();

        // Add the JWT filter before the UsernamePasswordAuthenticationFilter in the filter chain
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
