package com.bnp.arch.api.service;

import java.util.ArrayList;

import com.bnp.arch.api.model.user.User;
import com.bnp.arch.api.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading User with email " + email);
        User userRecord = null;
        try {
            userRecord = userRepository.getUserRecordByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Loaded User " + userRecord.toString());
        if (userRecord != null) {
            return new org.springframework.security.core.userdetails.User(email, userRecord.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
