package user;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.dto.CreateUserDto;
import user.dto.UserDto;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUser(String id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PatchMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok().body(userService.createUser(createUserDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser(String email) {
        this.userService.deleteUser(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/send/partner/request")
    public ResponseEntity<UserDto> sendPartnerRequest(String id, String partnerId) {
        this.userService.sendPartnerRequest(id,partnerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
