package com.PantryPal.auth;

import com.PantryPal.model.User;
import com.PantryPal.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AppUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findByEmail(username).isEmpty()){
            throw new UsernameNotFoundException("User with " + username + " Not Found.");
        }
        User user = userRepository.findByEmail(username).get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
