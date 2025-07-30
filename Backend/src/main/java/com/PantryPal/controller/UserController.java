package com.PantryPal.controller;
import com.PantryPal.auth.MyUserDetails;
import com.PantryPal.dto.LoginUserRequest;
import com.PantryPal.dto.RecipeDto;
import com.PantryPal.dto.UserReqBodyDto;
import com.PantryPal.dto.UserResBodyDto;
import com.PantryPal.model.User;
import com.PantryPal.repository.UserRepository;
import com.PantryPal.auth.AppUserDetailService;
import com.PantryPal.auth.JwtService;
import com.PantryPal.service.FavoriteService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

/**Figure out what way to implement Authentication and Authorization.
 * Authentication: State Management(Server Side Storage), Token-Based Auth(Ex: JWT)
 * Authorization: To Read...
 *
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Value("${security.jwt.expiration-time}")
    private long jwtTokenAge;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AppUserDetailService myUserDetailService;

    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AppUserDetailService myUserDetailService){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.myUserDetailService = myUserDetailService;
    }
    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserReqBodyDto userReqBodyDto){
        if(userRepository.findByEmail(userReqBodyDto.getEmail()).isPresent()){
            return new ResponseEntity<>("User with the email already exists.", HttpStatus.CONFLICT);
        }
        User user = new User(userReqBodyDto.getName(), userReqBodyDto.getEmail(), passwordEncoder.encode(userReqBodyDto.getPassword()));
        return new ResponseEntity<>("Created User successfully.", HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserRequest loginUserRequest, HttpServletResponse response){
    // Offset tokenAge from seconds to milliseconds for maxAge property
        long jwtTokenAgeSecs = jwtTokenAge / 1000;
        if (userRepository.findByEmail(loginUserRequest.getEmail()).isEmpty()){
            return new ResponseEntity<>("User not found.", HttpStatus.UNAUTHORIZED);
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserRequest.getEmail(),
                loginUserRequest.getPassword()
        ));

        try{
            String jwtToken = jwtService.generateToken(myUserDetailService.loadUserByUsername(loginUserRequest.getEmail()));
            ResponseCookie responseCookie = ResponseCookie
                    .from("JWT",jwtToken)
                    .secure(true)
                    .httpOnly(true)
                    .path("/")
                    .sameSite("None")
                    .maxAge(jwtTokenAgeSecs)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
            User user = userRepository.findByEmail(loginUserRequest.getEmail()).get();
            return new ResponseEntity<>("Login Successful.", HttpStatus.ACCEPTED);
        }
        catch(BadCredentialsException exception){
            log.info(exception.getMessage());
            return new ResponseEntity<>("Incorrect Email and Password.", HttpStatus.UNAUTHORIZED);
        }
        catch(Exception exception){
            return new ResponseEntity<>("Unexpected Internal Server Error. Please Try Again Later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/auth/status")
    public ResponseEntity<UserResBodyDto> verifyUser(){
        MyUserDetails curUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(new UserResBodyDto(curUser.getUsername()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/v1/user")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = checkValidUser(optionalUser);
        return buildUserResponseEntity(user);
    }

    @PutMapping("/api/v1/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserReqBodyDto userReqBodyDto){
        User curUser = checkValidUser(userRepository.findById(id));
        if(curUser == null){
            User newUser = new User(userReqBodyDto.getEmail(), passwordEncoder.encode(userReqBodyDto.getPassword()));
            return buildUserResponseEntity(newUser);
        }
        curUser.setEmail(userReqBodyDto.getEmail());
        curUser.setPassword(passwordEncoder.encode(userReqBodyDto.getPassword()));
        return buildUserResponseEntity(curUser);
    }

    @DeleteMapping("/api/v1/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable long id){
        User curUser = checkValidUser(userRepository.findById(id));
        if(curUser == null){
            return new ResponseEntity<>("User not found.",HttpStatus.ACCEPTED);
        }

        userRepository.deleteById(id);
        return new ResponseEntity<>("User deleted.",HttpStatus.ACCEPTED);
    }

//    @DeleteMapping("/api/v1/user")
//    public ResponseEntity<String> deleteUserByEmail(@RequestParam String email){
//        User curUser = checkValidUser(userRepository.findByEmail(email));
//        if(curUser == null){
//            return new ResponseEntity<>("User not found.",HttpStatus.ACCEPTED);
//        }
//
//        userRepository.deleteByEmail(email);
//        return new ResponseEntity<>("User deleted.",HttpStatus.ACCEPTED);    }

    private User checkValidUser(Optional<User> optionalUser){
        return optionalUser.orElse(null);
    }

    private ResponseEntity<User> buildUserResponseEntity(User user){
        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
