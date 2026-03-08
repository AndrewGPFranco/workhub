package com.agpf.workhub.application.usecases;

import com.agpf.workhub.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;
    private static final Pattern patternEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        if (isEmail(usernameOrEmail))
            return userRepositoryPort.findByEmail(usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail));

        return userRepositoryPort.findByUsername(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail));
    }

    public static boolean isEmail(String usernameOrEmail) {
        return patternEmail.matcher(usernameOrEmail).matches();
    }

}
