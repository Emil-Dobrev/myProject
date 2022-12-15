package com.example.myProject.user;

import com.example.myProject.enums.Provider;
import com.example.myProject.enums.Status;
import com.example.myProject.exceptions.EmailAlreadyTaken;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.myProject.partner.Partner;
import com.example.myProject.user.dto.CreateUserDto;
import com.example.myProject.user.dto.UserDto;

import java.rmi.server.UID;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static com.example.myProject.constants.Constants.EMAIL_ALREADY_TAKEN;
import static com.example.myProject.constants.Constants.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final ModelMapper modelMapper;
//    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
//        return new UserPrincipal(user);
//    }

    public String hi() {
        return "hello";
    }

    @Override
    public UserDto getUser(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
      Optional<User> checkEmail = this.userRepository.findByEmail(createUserDto.getEmail());
      if(checkEmail.isPresent()) {
          throw new EmailAlreadyTaken(EMAIL_ALREADY_TAKEN);
      }

        User user = this.modelMapper.map(createUserDto, User.class);
        user.setCreatedAt(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(Provider.LOCAL);
        this.userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(String id) {
      Optional<User> user =  userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

    @Override
    public UserDto updateUser(UID user) {
        return null;
    }

    @Override
    public void sendPartnerRequest(String id, String partnerId) {
        User user = this.userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
        User partner = this.userRepository.findById(partnerId).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
        user.setPartner(new Partner(partner.getName(), partner.getEmail(), Status.WAITING));
        userRepository.save(user);
    }

    @Override
    public UserDto acceptPartnerRequest(String id) {
        User user = this.userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
        Partner partner = user.getPartner();
        partner.setStatus(Status.ACCEPTED);
        user.setPartner(partner);
        this.userRepository.save(user);
       Optional<User> secondUser = this.userRepository.findByEmail(partner.getEmail());
        secondUser.ifPresent(value -> {
            value.setPartner(modelMapper.map(user, Partner.class));
            value.getPartner().setStatus(Status.ACCEPTED);
        } );
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto declinePartnerRequest(String id) {
        User user = this.userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
        user.setPartner(null);
        this.userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }
}
