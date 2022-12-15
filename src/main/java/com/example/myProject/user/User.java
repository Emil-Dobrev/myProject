package com.example.myProject.user;

import com.example.myProject.enums.Provider;
import com.example.myProject.enums.Role;
import com.example.myProject.partner.Partner;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
@Builder
public class User {

    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String email;
    private String password;
    private String name;
    private Provider provider;
    private Instant createdAt;
    private Partner partner;
    private Role role = Role.USER;
}
