package mx.tecnm.backend.api.dto;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UsuarioRequest(
    @NotBlank String nombre,
    @Email @NotBlank String email,
    @NotBlank String telefono,
    @NotBlank @Pattern(regexp = "H|M") String sexo,
    @NotBlank String contrasena,
    @NotNull Date fechaNacimiento
) {}
