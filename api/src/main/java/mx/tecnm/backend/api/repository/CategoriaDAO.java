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
        String sql = "SELECT id, nombre, activo FROM categorias WHERE activo";

        return conexion.sql(sql)
                .query((rs, rowNum) -> new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ))
                .list();
    }

    // --------- CONSULTAR POR ID ---------
    public Categoria consultarCategoriaPorId(int id) {
        String sql = "SELECT id, nombre, activo FROM categorias WHERE id = ? AND activo";

        return conexion.sql(sql)
                .param(id)
                .query((rs, rowNum) -> new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ))
                .optional()
                .orElse(null);
    }

    // --------- CREAR ---------
    public Categoria crearCategoria(String nombre) {
        String sql = """
            INSERT INTO categorias (nombre)
            VALUES (?)
            RETURNING id, nombre
            """;

        return conexion.sql(sql)
                .param(nombre)
                .query((rs, rowNum) -> new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ))
                .single();
    }

    // --------- ACTUALIZAR ---------
    public Categoria actualizarCategoria(int id, String nombre) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ? AND activo";

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
        String sql = "UPDATE categorias SET activo = false WHERE id = ?";

        int filas = conexion.sql(sql)
                .param(id)
                .update();

        return filas > 0;
    }
}

