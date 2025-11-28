package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Pedido;

@Repository
public class PedidoDAO {

     @Autowired
    private JdbcClient conexion;

    public List<Pedido> consultarPedidos() {

        String sql = "SELECT id, fecha, fecha_hora_pago, importe_envio, importe_iva, importe_productos, metodos_pago_id, numero, total, usuarios_id FROM pedidos";
        return conexion.sql(sql)
                .query((rs, rowNum) -> new Pedido(
                rs.getInt("id"),
                rs.getTimestamp("fecha"),
                rs.getTimestamp("fecha_hora_pago"),
                rs.getDouble("importe_envio"),
                rs.getDouble("importe_iva"),
                rs.getDouble("importe_productos"),
                rs.getInt("metodos_pago_id"),
                rs.getString("numero"),
                rs.getDouble("total"),
                rs.getInt("usuarios_id")
        ))
        .list();
    }

}
