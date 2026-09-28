package com.retrogamer.inventory_manager.service.impl;

import com.retrogamer.inventory_manager.dto.request.LoginRequest;
import com.retrogamer.inventory_manager.dto.response.LoginResponse;
import com.retrogamer.inventory_manager.dto.request.RegisterRequest;
import com.retrogamer.inventory_manager.model.User;
import com.retrogamer.inventory_manager.model.enums.Role;
import com.retrogamer.inventory_manager.repository.UserRepository;
import com.retrogamer.inventory_manager.security.JwtTokenProvider;
import com.retrogamer.inventory_manager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;


/* --------LOGIN--------------------------------------------------------------------------------------------- */

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 1. Autentica l'utente (confronta password inviata con hash BCrypt nel DB)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Genera il token JWT per l'utente autenticato
        String token = tokenProvider.generateToken(authentication);

        // 3. Recupera i dati profilo dell'utente da restituire nella risposta
        User user = userRepository.findByUsername(loginRequest.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(loginRequest.getUsernameOrEmail())
                        .orElseThrow(() -> new RuntimeException("Utente non trovato")));

        // 4. Compila la LoginResponse
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    /* -----REGISTER-------------------------------------------------------------------------------------------------------------- */


    @Override
    public LoginResponse register(RegisterRequest registerRequest) {
        // 1. Controllo unicità username ed email
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username già in uso!");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email già in uso!");
        }

        // 2. Creazione dell'entità User con Hashing BCrypt della password
        User user = User.builder()
                .username(registerRequest.getUsername())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .gender(registerRequest.getGender())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        // 3. Login automatico: verifica credenziali tramite AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. Generazione del Token JWT e costruzione della LoginResponse
        String token = tokenProvider.generateToken(authentication);

        return LoginResponse.builder()
                .token(token)
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

}