package com.noter.api.auth;

import java.util.Optional;

public interface UserDao {

	Optional<User> getUserByName(String name);
}
