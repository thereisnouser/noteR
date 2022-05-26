package com.noter.api.users.dao;

import static com.noter.api.security.UserRole.ADMIN;
import static com.noter.api.security.UserRole.GUEST;
import static com.noter.api.security.UserRole.USER;
import com.noter.api.users.model.User;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("fake")
public class FakeUserDao implements UserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeUserDao(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserByName(final String name) {
        return this.getAllUsers()
            .stream()
            .filter(user -> user.getUsername().equals(name))
            .findFirst();
    }

    private List<User> getAllUsers() {
        return Arrays.asList(
            new User(
                "guest",
                passwordEncoder.encode("guest"),
                GUEST.getAuthorities(),
                true,
                true,
                true,
                true
            ),
            new User(
                "user",
                passwordEncoder.encode("user"),
                USER.getAuthorities(),
                true,
                true,
                true,
                true
            ),
            new User(
                "admin",
                passwordEncoder.encode("admin"),
                ADMIN.getAuthorities(),
                true,
                true,
                true,
                true
            )
        );
    }
}
