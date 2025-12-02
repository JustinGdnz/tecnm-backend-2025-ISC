package mx.tecnm.backend.api.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.sql.Date;

import mx.tecnm.backend.api.models.Usuario;

@Repository
public class UsuarioDAO {

        @Autowired
        private JdbcClient conexion;

        public List<Usuario> consultarUsuarios() {
                String sql = "SELECT id, nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro FROM usuarios WHERE activo=true";

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

        public Usuario consultarUsuarioPorId(int id) {
                String sql = "SELECT id, nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro FROM usuarios  WHERE activo=true AND id = ?";

                return conexion.sql(sql)
                                .param(id)
                                .query((rs, rowNum) -> new Usuario(
                                                rs.getInt("id"),
                                                rs.getString("nombre"),
                                                rs.getString("email"),
                                                rs.getString("telefono"),
                                                rs.getString("sexo"),
                                                rs.getDate("fecha_nacimiento"),
                                                rs.getString("contrasena"),
                                                rs.getTimestamp("fecha_registro")))
                                .optional()
                                .orElse(null);
        }

        public Usuario crearUsuario(String nombre, String email, String telefono, String sexo, Date fecha_nacimiento,
                        String contrasena) {

                String sql = "INSERT INTO usuarios (nombre, email, telefono, sexo, fecha_nacimiento, contrasena) VALUES (?, ?, ?, CAST(? AS sexo_enum), ?, ?) RETURNING id, nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro";

                return conexion.sql(sql)
                                .params(nombre, email, telefono, sexo, fecha_nacimiento, contrasena)
                                .query((rs, rowNum) -> new Usuario(
                                                rs.getInt("id"),
                                                rs.getString("nombre"),
                                                rs.getString("email"),
                                                rs.getString("telefono"),
                                                rs.getString("sexo"),
                                                rs.getDate("fecha_nacimiento"),
                                                rs.getString("contrasena"),
                                                rs.getTimestamp("fecha_registro")))
                                .single();
        }

        public Usuario actualizarUsuario(Usuario u) {
                String sql = " UPDATE usuarios SET nombre = ?, email = ?, telefono = ?, sexo = CAST(? AS sexo_enum), fecha_nacimiento = ?, contrasena = ? WHERE activo=true AND id = ?";

                int filas = conexion.sql(sql)
                                .params(
                                                u.nombre(),
                                                u.email(),
                                                u.telefono(),
                                                u.sexo(),
                                                u.fecha_nacimiento(),
                                                u.contrasena(),
                                                u.id())
                                .update();

                if (filas == 0)
                        return null;

                return consultarUsuarioPorId(u.id());
        }

        public boolean eliminarUsuario(int id) {
                String sql = "UPDATE usuarios SET activo = false WHERE id = ?";

                int filas = conexion.sql(sql)
                                .param(id)
                                .update();

                return filas > 0;
        }

}
