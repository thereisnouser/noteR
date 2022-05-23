package com.noter.api.security;

public enum UserPermission {

	NOTE_READ("note:read"),
	NOTE_CREATE("note:create"),
	NOTE_UPDATE("note:update"),
	NOTE_DELETE("note:delete"),

	USER_READ("user:read"),
	USER_CREATE("user:create"),
	USER_DELETE("user:delete");

	private final String permission;

	private UserPermission(final String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return this.permission;
	}
}
