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

    // Obtener productos activos
    public List<Producto> consultarProductos() {

        String sql = """
            SELECT id, nombre, descripcion, marca, color, precio, peso, alto, ancho, profundidad, categorias_id, sku 
            FROM productos 
            WHERE activo = true
        """;

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

    // Obtener producto por ID
    public Producto consultarProductoPorId(int id) {

        String sql = """
            SELECT id, nombre, descripcion, marca, color, precio, peso, alto, ancho, profundidad, categorias_id, sku
            FROM productos 
            WHERE activo = true AND id = ?
        """;

        return conexion.sql(sql)
                .param(id)
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
                .optional()
                .orElse(null);
    }

    // Crear producto
    public Producto crearProducto(Producto p) {

        String sql = """
            INSERT INTO productos (nombre, descripcion, marca, color, precio, peso, alto, ancho, profundidad, categorias_id, sku)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id, nombre, descripcion, marca, color, precio, peso, alto, ancho, profundidad, categorias_id, sku
        """;

        return conexion.sql(sql)
                .params(
                        p.nombre(),
                        p.descripcion(),
                        p.marca(),
                        p.color(),
                        p.precio(),
                        p.peso(),
                        p.alto(),
                        p.ancho(),
                        p.profundidad(),
                        p.categorias_id(),
                        p.sku()
                )
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
                .single();
    }

    // Actualizar producto
    public Producto actualizarProducto(Producto p) {

        String sql = """
            UPDATE productos SET 
                nombre = ?, 
                descripcion = ?, 
                marca = ?, 
                color = ?, 
                precio = ?, 
                peso = ?, 
                alto = ?, 
                ancho = ?, 
                profundidad = ?, 
                categorias_id = ?, 
                sku = ?
            WHERE id = ? AND activo = true
        """;

        int filas = conexion.sql(sql)
                .params(
                        p.nombre(),
                        p.descripcion(),
                        p.marca(),
                        p.color(),
                        p.precio(),
                        p.peso(),
                        p.alto(),
                        p.ancho(),
                        p.profundidad(),
                        p.categorias_id(),
                        p.sku(),
                        p.id()
                )
                .update();

        if (filas == 0)
            return null;

        return consultarProductoPorId(p.id());
    }

    // Eliminar (soft delete)
    public boolean eliminarProducto(int id) {

        String sql = "UPDATE productos SET activo = false WHERE id = ?";

        int filas = conexion.sql(sql)
                .param(id)
                .update();

        return filas > 0;
    }
}
