package com.noter.api.security;

import static com.noter.api.security.UserPermission.NOTE_CREATE;
import static com.noter.api.security.UserPermission.NOTE_DELETE;
import static com.noter.api.security.UserPermission.NOTE_READ;
import static com.noter.api.security.UserPermission.NOTE_UPDATE;
import static com.noter.api.security.UserPermission.USER_CREATE;
import static com.noter.api.security.UserPermission.USER_DELETE;
import static com.noter.api.security.UserPermission.USER_READ;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

    GUEST(new HashSet<UserPermission>(Arrays.asList(
        NOTE_READ,
        USER_READ,
        USER_CREATE
    ))),
    USER(new HashSet<UserPermission>(Arrays.asList(
        NOTE_READ,
        NOTE_CREATE,
        NOTE_UPDATE,
        USER_READ
    ))),
    ADMIN(new HashSet<UserPermission>(Arrays.asList(
        NOTE_READ,
        NOTE_DELETE,
        USER_READ,
        USER_CREATE,
        USER_DELETE
    )));

    private Set<UserPermission> permissions;

    private UserRole(final Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        final Set<SimpleGrantedAuthority> authorities = this.getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
