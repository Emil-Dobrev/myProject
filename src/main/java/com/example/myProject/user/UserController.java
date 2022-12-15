package com.example.myProject.user;

import com.example.myProject.user.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.myProject.user.dto.UserDto;


@RequestMapping("api/v1/users")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/hi")
    public ResponseEntity<String> sayHi(){
        return ResponseEntity.ok().body(userService.hi());
    }

    @GetMapping(path = "test")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok().body(userService.getUser(authentication.name()));
    }

    @PostMapping("/create")
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
    @GetMapping("/partner/accept")
    public ResponseEntity<UserDto> acceptPartnerRequest(String id ) {
    return ResponseEntity.ok().body(this.userService.acceptPartnerRequest(id));
    }

    @GetMapping("/partner/decline")
    public ResponseEntity<UserDto> declinePartnerRequest(String id) {
        return ResponseEntity.ok().body(this.userService.declinePartnerRequest(id));
    }


}
