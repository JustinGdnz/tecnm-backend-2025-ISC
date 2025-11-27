package mx.tecnm.backend.api.models;

public record Productos(int id, String nombre, String descripcion, String marca, String color, double precio, double peso,
          double alto, double ancho, double profundidad, int categorias_id, String sku) {

}
