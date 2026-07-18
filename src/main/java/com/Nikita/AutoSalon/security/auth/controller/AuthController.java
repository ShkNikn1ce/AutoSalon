package com.Nikita.AutoSalon.security.auth.controller;

import com.Nikita.AutoSalon.security.auth.dto.AuthResponse;
import com.Nikita.AutoSalon.security.auth.service.AuthenticationService;
import com.Nikita.AutoSalon.security.auth.dto.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        return authenticationService.login(request);
    }
}
