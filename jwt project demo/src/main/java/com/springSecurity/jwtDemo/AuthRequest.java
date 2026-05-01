package com.springSecurity.jwtDemo;

import lombok.*;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthRequest {

    String username;
    String password;
}
