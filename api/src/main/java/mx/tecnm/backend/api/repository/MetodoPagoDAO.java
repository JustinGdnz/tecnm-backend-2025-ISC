package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.MetodoPago;

@Repository
public class MetodoPagoDAO {

    @Autowired
    private JdbcClient conexion;

    public List<MetodoPago> consultarMetodoPago() {
        String sql = "SELECT id, nombre, comision FROM metodos_pago";

        return conexion.sql(sql)
                .query((rs, rowNum) -> new MetodoPago(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("comision")
                ))
                .list();
    }

    public MetodoPago consultarMetodoPagoPorId(int id) {
        String sql = "SELECT id, nombre, comision FROM metodos_pago WHERE id = ?";

        return conexion.sql(sql)
                .param(id)
                .query((rs, rowNum) -> new MetodoPago(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("comision")
                ))
                .optional()
                .orElse(null);
    }

    public MetodoPago crearMetodoPago(String nombre, double comision) {
    String sql = " INSERT INTO metodos_pago (nombre, comision) VALUES (?, ?) RETURNING id, nombre, comision";

    return conexion.sql(sql)
            .params(nombre, comision)
            .query((rs, rowNum) -> new MetodoPago(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("comision")
            ))
            .single();
}

   public MetodoPago actualizarMetodosPago(MetodoPago mp) {
    String sql = "UPDATE metodos_pago SET nombre = ?, comision = ? WHERE id = ?";

    int filas = conexion.sql(sql)
            .params(mp.nombre(), mp.comision(), mp.id())
            .update();

    if (filas == 0) {
        return null;
    }

    return consultarMetodoPagoPorId(mp.id());
}

public boolean eliminarMetodoPago(int id) {
    String sql = "DELETE FROM metodos_pago WHERE id = ?";

    int filas = conexion.sql(sql)
            .param(id)
            .update();

    return filas > 0; // true si se eliminó, false si no existía
}

}
