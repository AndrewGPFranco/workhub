package com.agpf.workhub.domain.ports.out;

import com.agpf.workhub.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    User save(User user);
}
