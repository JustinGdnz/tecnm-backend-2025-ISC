package mx.tecnm.backend.api.dto;

public record DetalleCarritoRequest (
    int productoID,
    int cantidad
) {}
