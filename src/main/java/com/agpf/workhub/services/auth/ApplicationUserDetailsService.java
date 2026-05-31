package com.agpf.workhub.services.auth;

import com.agpf.workhub.repositories.auth.UserRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return User.withUsername(user.getEmail()).password(user.getPasswordHash()).roles(user.getRole()).build();
    }
}
