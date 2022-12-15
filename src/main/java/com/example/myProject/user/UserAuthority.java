package com.example.myProject.user;

import com.example.myProject.enums.Role;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

   private final Role role;

    public UserAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role.toString();
    }
}
