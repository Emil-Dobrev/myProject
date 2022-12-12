package user.dto;

import lombok.Data;
import lombok.NonNull;
@Data
public class CreateUserDto {

    @NonNull
    private String email;
    @NonNull
    private String fullName;
    private String password;

}
