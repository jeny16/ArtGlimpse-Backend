package com.artglimpse.security;

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

import com.artglimpse.authentication.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class UnifiedSecurityConfig {

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

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
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
                .antMatchers(HttpMethod.GET, "/api/chat").permitAll()
                .antMatchers(HttpMethod.POST, "/api/chat").permitAll()
                .antMatchers(HttpMethod.POST, "/api/seller/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/stats").permitAll()  // Any logged-in user can view stats
                // .antMatchers(HttpMethod.POST, "/api/stats").hasRole("SELLER")  // Only sellers can update stats
                // .antMatchers(HttpMethod.PUT, "/api/stats").hasRole("SELLER")  // Only sellers can modify stats
                .antMatchers("/seller/**").hasRole("SELLER")
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
