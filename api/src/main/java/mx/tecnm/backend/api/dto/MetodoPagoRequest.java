package mx.tecnm.backend.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MetodoPagoRequest(
    @NotBlank String nombre,
    @NotNull Double comision
) {}
