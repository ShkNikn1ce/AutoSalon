package com.Nikita.AutoSalon.security.auth.service;

import com.Nikita.AutoSalon.security.auth.dto.AuthResponse;
import com.Nikita.AutoSalon.security.auth.dto.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return new AuthResponse("Авторизация успешна!");
    }
}
