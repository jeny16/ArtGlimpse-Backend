// package com.artglimpse.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.*;
// import org.springframework.stereotype.Service;

// import com.artglimpse.repository.UserRepository;
// import com.artglimpse.model.User;

// import java.util.ArrayList;
// import java.util.List;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//         List<GrantedAuthority> authorities = new ArrayList<>();
//         authorities.add(new SimpleGrantedAuthority(user.getRole()));
//         return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                 authorities);
//     }
// }

package com.artglimpse.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.artglimpse.model.auth.User;
import com.artglimpse.repository.auth.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Look up the user using the email field instead of username
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        // Return a UserDetails instance with the email as the principal
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
