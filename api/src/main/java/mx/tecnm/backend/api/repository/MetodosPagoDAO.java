package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.MetodosPago;

@Repository
public class MetodosPagoDAO {
     @Autowired
    private JdbcClient conexion;

    public List<MetodosPago> consultarMetodosPago() {

       String sql = "SELECT id, nombre, comision FROM metodos_pago";
        return conexion.sql(sql)
                .query((rs, rowNum) -> new MetodosPago(
                       rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("comision")
                ))
                .list();
    }

}
