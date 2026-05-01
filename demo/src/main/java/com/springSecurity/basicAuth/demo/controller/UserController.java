package com.springSecurity.basicAuth.demo.controller;

import com.springSecurity.basicAuth.demo.entity.UserEntity;
import com.springSecurity.basicAuth.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping ("/encodePassword")
    public void saveUserWithEncodedPassword(@RequestParam String username, @RequestParam String password){

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);

        userRepository.save(user);
    }
}
