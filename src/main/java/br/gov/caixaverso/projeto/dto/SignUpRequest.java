package br.gov.caixaverso.projeto.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

public record SignUpRequest(
        @NotBlank
        @NotEmpty
        @NotNull
        String name,

        @Email
        String email,


        @NotBlank
        @NotEmpty
        @NotNull
        @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres")
        String password) {
}
