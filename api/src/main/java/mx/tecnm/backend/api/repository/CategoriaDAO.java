package mx.tecnm.backend.api.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Categoria;

@Repository
public class CategoriaDAO {

    @Autowired
    private JdbcClient conexion;

    // --------- CONSULTAR TODOS ---------
    public List<Categoria> consultarCategorias() {
        String sql = "SELECT id, nombre, activo FROM categorias";

        return conexion.sql(sql)
                .query((rs, rowNum) -> new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBoolean("activo")
                ))
                .list();
    }

    // --------- CONSULTAR POR ID ---------
    public Categoria consultarCategoriaPorId(int id) {
        String sql = "SELECT id, nombre, activo FROM categorias WHERE id = ?";

        return conexion.sql(sql)
                .param(id)
                .query((rs, rowNum) -> new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBoolean("activo")
                ))
                .optional()
                .orElse(null);
    }

    // --------- CREAR ---------
    public Categoria crearCategoria(String nombre) {
        String sql = """
            INSERT INTO categorias (nombre)
            VALUES (?)
            RETURNING id, nombre, activo
            """;

        return conexion.sql(sql)
                .param(nombre)
                .query((rs, rowNum) -> new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBoolean("activo")
                ))
                .single();
    }

    // --------- ACTUALIZAR ---------
    public Categoria actualizarCategoria(int id, String nombre) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";

        int filas = conexion.sql(sql)
                .params(nombre, id)
                .update();

        if (filas == 0) {
            return null;
        }

        return consultarCategoriaPorId(id);
    }

    // --------- ELIMINAR ---------
    public boolean eliminarCategoria(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";

        int filas = conexion.sql(sql)
                .param(id)
                .update();

        return filas > 0;
    }
}

