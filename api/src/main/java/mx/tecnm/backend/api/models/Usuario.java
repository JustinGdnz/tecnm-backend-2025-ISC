package mx.tecnm.backend.api.models;
import java.sql.Date;
import java.sql.Timestamp;

public record Usuario(int id, String nombre, String email, String telefono , String sexo,
           Date fecha_nacimiento,String contrasena, Timestamp fecha_registro) {

}
