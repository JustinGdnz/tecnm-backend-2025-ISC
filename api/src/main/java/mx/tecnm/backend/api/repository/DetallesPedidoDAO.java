package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.DetallesPedido;

@Repository
public class DetallesPedidoDAO {

    @Autowired
    private JdbcClient conexion;

    public List<DetallesPedido> obtenerDetallesPedido() {
        String sql = "SELECT id, cantidad, precio, productos_id, pedidos_id FROM detalles_pedido";

        return conexion.sql(sql)
                .query((rs, rowNum) -> new DetallesPedido(
                        rs.getInt("id"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getInt("productos_id"),
                        rs.getInt("pedidos_id")))
                .list();

    }

}
