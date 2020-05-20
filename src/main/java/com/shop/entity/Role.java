package com.shop.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    String name;

    Role(String role) {
        this.name = role;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
