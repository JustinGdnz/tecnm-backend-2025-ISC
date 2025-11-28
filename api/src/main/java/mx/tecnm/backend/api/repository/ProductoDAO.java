package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Producto;

@Repository
public class ProductoDAO {
    @Autowired
    private JdbcClient conexion;

    public List<Producto> consultarProductos() {

        String sql = "SELECT id, nombre, descripcion, marca, color, precio, peso,alto, ancho, profundidad, categorias_id, sku FROM productos";
        return conexion.sql(sql)
                .query((rs, rowNum) -> new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("marca"),
                        rs.getString("color"),
                        rs.getDouble("precio"),
                        rs.getDouble("peso"),
                        rs.getDouble("alto"),
                        rs.getDouble("ancho"),
                        rs.getDouble("profundidad"),
                        rs.getInt("categorias_id"),
                        rs.getString("sku")))
                .list();
    }

}
