package com.example.myProject.user.dto;

import com.example.myProject.partner.Partner;
import lombok.Data;

@Data
public class UserDto {

    private String name;
    private Partner partner;
}
