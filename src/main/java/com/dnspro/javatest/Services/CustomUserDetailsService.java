package com.dnspro.javatest.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dnspro.javatest.Repository.UserRepository;
import com.dnspro.javatest.User.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;

    // public UserDetails getUserDetails(String username) {
    //     return userRepository.findByUsername(username)
    //     .orElseThrow(()-> new UsernameNotFoundException("Username tidak ditemukan") );
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Load User by Username di custom userdetailsservice");
        return userRepository.findByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("Username tidak ditemukan") );
    }
}
