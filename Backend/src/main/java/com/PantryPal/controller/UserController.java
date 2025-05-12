package com.PantryPal.controller;

import com.PantryPal.dto.CreateUserReqBodyDto;
import com.PantryPal.model.User;
import com.PantryPal.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = checkValidUser(optionalUser);
        return buildUserResponseEntity(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        Optional<User> optionalUser = userRepository.findById(id);
        User user = checkValidUser(optionalUser);
        return buildUserResponseEntity(user);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody CreateUserReqBodyDto userReqBodyDto){
        User user = new User(userReqBodyDto.getEmail(), userReqBodyDto.getPassword());

        return buildUserResponseEntity(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody CreateUserReqBodyDto createUserReqBodyDto){
        User curUser = checkValidUser(userRepository.findById(id));
        if(curUser == null){
            User newUser = new User(createUserReqBodyDto.getEmail(), createUserReqBodyDto.getPassword());
            return buildUserResponseEntity(newUser);
        }
        curUser.setEmail(createUserReqBodyDto.getEmail());
        curUser.setPassword(createUserReqBodyDto.getPassword());
        return buildUserResponseEntity(curUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable long id){
        User curUser = checkValidUser(userRepository.findById(id));
        if(curUser == null){
            return new ResponseEntity<>("User not found.",HttpStatus.ACCEPTED);
        }

        userRepository.deleteById(id);
        return new ResponseEntity<>("User deleted.",HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user/")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam String email){
        User curUser = checkValidUser(userRepository.findByEmail(email));
        if(curUser == null){
            return new ResponseEntity<>("User not found.",HttpStatus.ACCEPTED);
        }

        userRepository.deleteByEmail(email);
        return new ResponseEntity<>("User deleted.",HttpStatus.ACCEPTED);    }

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
