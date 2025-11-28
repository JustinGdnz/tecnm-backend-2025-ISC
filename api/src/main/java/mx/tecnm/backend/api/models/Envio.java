package mx.tecnm.backend.api.models;

import java.sql.Timestamp;

public record Envio(int id, int domicilios_id, String estado, Timestamp fecha, Timestamp fecha_entrega, String numero_seguimiento, int pedidos_id) {



}
