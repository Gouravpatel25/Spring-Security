package com.springSecurity.jwtDemo.service;

import com.springSecurity.jwtDemo.entity.UserEntity;
import com.springSecurity.jwtDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    public UserEntity getUserFromUsername(String username){
        return  userRepository.
                findByUsernameAndIsActive(username,true)
                .orElseThrow( () -> new UsernameNotFoundException("User not found with username: " + username));
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = getUserFromUsername(username);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(
                new SimpleGrantedAuthority(user.getRole().name())
        );

        user.getRole().getPermissions().forEach(permission -> {
            authorities.add(
                    new SimpleGrantedAuthority(permission.name())
            );
        });

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

    }

}
