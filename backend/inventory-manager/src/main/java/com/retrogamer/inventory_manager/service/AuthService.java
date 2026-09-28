package com.retrogamer.inventory_manager.service;

import com.retrogamer.inventory_manager.dto.request.LoginRequest;
import com.retrogamer.inventory_manager.dto.response.LoginResponse;
import com.retrogamer.inventory_manager.dto.request.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse register(RegisterRequest registerRequest);
}