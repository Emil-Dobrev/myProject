package com.example.myProject.partner;

import com.example.myProject.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Partner {

    private String fullName;
    private String email;
    private Status status;
}
