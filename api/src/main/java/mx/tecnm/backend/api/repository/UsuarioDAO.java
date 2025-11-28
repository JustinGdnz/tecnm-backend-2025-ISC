package mx.tecnm.backend.api.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Usuario;

@Repository
public class UsuarioDAO {
    @Autowired
    private JdbcClient conexion;

    public List<Usuario> consultarUsuarios() {
        String sql = "SELECT id, nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro FROM usuarios";
        return conexion.sql(sql)
                .query((rs, rowNum) -> new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("sexo"),
                        rs.getDate("fecha_nacimiento"),
                        rs.getString("contrasena"),
                        rs.getTimestamp("fecha_registro")))
                .list();
    }

}
