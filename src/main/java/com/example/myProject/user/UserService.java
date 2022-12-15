package com.example.myProject.user;

import com.example.myProject.user.dto.CreateUserDto;
import com.example.myProject.user.dto.UserDto;

import java.rmi.server.UID;

public interface UserService {

    UserDto getUser(String id);
    UserDto createUser(CreateUserDto user);
    void deleteUser(String id);
    UserDto updateUser(UID user);
    void sendPartnerRequest(String id , String partnerId);
    UserDto acceptPartnerRequest(String id);
    UserDto declinePartnerRequest(String id);
    String hi();
}
