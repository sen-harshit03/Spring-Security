package com.example.SpringSecurityS2.service;

import com.example.SpringSecurityS2.repository.UserRepository;
import com.example.SpringSecurityS2.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from repo
        var dbUser = userRepository.findUserByUsername(username);

        // Map it to securityUser(username, password) which is the wrapper for User
        return dbUser.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found:" + username));
    }
}
