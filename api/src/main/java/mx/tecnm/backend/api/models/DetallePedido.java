package mx.tecnm.backend.api.models;

public record DetallePedido(int id, int cantidad, double precio, int productos_id, int ventas_id) {

}
