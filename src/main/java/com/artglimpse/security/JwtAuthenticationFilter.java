// package com.artglimpse.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.artglimpse.authentication.service.CustomUserDetailsService;

// import javax.servlet.*;
// import javax.servlet.http.*;
// import java.io.IOException;

// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     @Autowired
//     private JwtTokenUtil jwtTokenUtil;
//     @Autowired
//     private CustomUserDetailsService customUserDetailsService;
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         String jwt = getJwtFromRequest(request);
//         if (StringUtils.hasText(jwt)) {
//             try {
//                 String userId = jwtTokenUtil.getUsernameFromToken(jwt);
//                 UserDetails userDetails = customUserDetailsService.loadUserById(userId);
//                 if (jwtTokenUtil.validateToken(jwt, userDetails)) {
//                     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                             userDetails, null, userDetails.getAuthorities());
//                     authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                     SecurityContextHolder.getContext().setAuthentication(authentication);
//                 }
//             } catch (Exception ex) {
//                 System.out.println("Could not set user authentication: " + ex);
//             }
//         }
//         filterChain.doFilter(request, response);
//     }

//     private String getJwtFromRequest(HttpServletRequest request) {
//         String bearerToken = request.getHeader("Authorization");
//         if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//             return bearerToken.substring(7);
//         }
//         return null;
//     }
// }

package com.artglimpse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.artglimpse.authentication.service.CustomUserDetailsService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.hasText(jwt)) {
            try {
                String userId = jwtTokenUtil.getUsernameFromToken(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                // Log the error (optional)
                System.out.println("JWT Authentication error: " + ex.getMessage());
                // Send a 401 Unauthorized response if token is malformed or invalid
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing JWT token");
                return; // Stop further processing
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
