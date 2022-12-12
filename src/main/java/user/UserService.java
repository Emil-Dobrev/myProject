package user;

import user.dto.CreateUserDto;
import user.dto.UserDto;

import java.rmi.server.UID;

public interface UserService {

    UserDto getUser(String id);
    UserDto createUser(CreateUserDto user);
    void deleteUser(String id);
    UserDto updateUser(UID user);
    void sendPartnerRequest(String id , String UID);
    UserDto acceptPartnerRequest(String id);
}
