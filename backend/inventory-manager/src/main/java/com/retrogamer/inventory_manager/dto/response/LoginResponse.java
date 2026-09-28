package com.retrogamer.inventory_manager.dto.response;

import com.retrogamer.inventory_manager.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;

    @Builder.Default
    private String tokenType = "Bearer";

    private Long id;
    private String username;
    private String email;
    private Role role;
}