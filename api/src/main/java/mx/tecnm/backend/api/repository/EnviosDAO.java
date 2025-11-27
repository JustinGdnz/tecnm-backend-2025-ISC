package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Envios;

@Repository
public class EnviosDAO {

    @Autowired
    private JdbcClient conexion;

    public List<Envios> consultarEnvios() {

        String sql = "SELECT id, domicilios_id, estado, fecha, fecha_entrega, numero_seguimiento, pedidos_id FROM envios";

        return conexion.sql(sql)
                .query((rs, rowNum) -> new Envios(
                        rs.getInt("id"),
                        rs.getInt("domicilios_id"),
                        rs.getString("estado"),
                        rs.getTimestamp("fecha"),
                        rs.getTimestamp("fecha_entrega"),
                        rs.getString("numero_seguimiento"),
                        rs.getInt("pedidos_id")
                ))
                .list();
    }
}
