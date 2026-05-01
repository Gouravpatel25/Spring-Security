package com.springSecurity.jwtDemo.controller;

import com.springSecurity.jwtDemo.AuthRequest;
import com.springSecurity.jwtDemo.entity.Role;
import com.springSecurity.jwtDemo.entity.UserEntity;
import com.springSecurity.jwtDemo.repository.UserRepository;
import com.springSecurity.jwtDemo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;


    @GetMapping ("/encodePassword")
    public void saveUserWithEncodedPassword(@RequestParam String username, @RequestParam String password, @RequestParam Role role){

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        user.setRole(role);

        userRepository.save(user);
    }

    @PostMapping("/generateToken")
    public String authenticate(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername()
                        , authRequest.getPassword()));

        if (authenticate.isAuthenticated()){
            String role = authenticate
                        .getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        .replace("ROLE_","");

            return jwtService.generateToken(authRequest.getUsername(), role);
        }

        return "Couldn't authenticate";
    }
}
