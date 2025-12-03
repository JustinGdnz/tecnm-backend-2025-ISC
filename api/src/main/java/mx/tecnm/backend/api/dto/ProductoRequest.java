package mx.tecnm.backend.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductoRequest(
    @NotBlank String nombre,
    @NotBlank String descripcion,
    @NotBlank String marca,
    @NotBlank String color,
    double precio,
    double peso,
    double alto,
    double ancho,
    double profundidad,
    @NotBlank String sku,
    int categoriasID
) {}
