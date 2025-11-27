package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Domicilio;

@Repository
public class DomicilioDAO {

    @Autowired
    private JdbcClient conexion;

    public List<Domicilio> consultarDomicilios() {
        String sql = "SELECT id, calle, numero, colonia, ciudad, estado, cp, usuarios_id FROM domicilios";
        return conexion.sql(sql)
                .query((rs, rowNum) -> new Domicilio(
                    rs.getInt("id"),
                    rs.getString("calle"),
                    rs.getString("numero"),
                    rs.getString("colonia"),
                    rs.getString("ciudad"),
                    rs.getString("estado"),
                    rs.getString("cp"),
                    rs.getInt("usuarios_id")))
                    .list();

    }
}
