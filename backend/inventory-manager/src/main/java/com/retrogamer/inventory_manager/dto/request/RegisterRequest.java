package com.retrogamer.inventory_manager.dto.request;

import com.retrogamer.inventory_manager.model.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username obbligatorio")
    @Size(min = 3, max = 30, message = "Username deve essere tra 3 e 30 caratteri")
    private String username;

    @NotBlank(message = "Nome obbligatorio")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Cognome obbligatorio")
    @Size(max = 50)
    private String lastName;

    @NotNull(message = "Genere obbligatorio (M, F, OTHER)")
    private Gender gender;

    @NotBlank(message = "Email obbligatoria")
    @Email(message = "Formato email non valido")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "Password obbligatoria")
    @Size(min = 6, max = 100, message = "La password deve contenere almeno 6 caratteri")
    private String password;
}