package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.MetodoPago;
import mx.tecnm.backend.api.repository.MetodoPagoDAO;

@RestController
@RequestMapping("/metodospago")
public class MetodoPagoController {

    @Autowired
    MetodoPagoDAO repo;

    @GetMapping()
    public ResponseEntity<List<MetodoPago>> obtenerMetodoPago() {
        List<MetodoPago> resultado = repo.consultarMetodoPago();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> obtenerMetodoPagoPorId(@PathVariable int id){
        MetodoPago resultado = repo.consultarMetodoPagoPorId(id);

        if(resultado == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultado);
        }
    }

    @PostMapping ("/crear")
    public ResponseEntity<MetodoPago> crearMetodoPago(@RequestParam String nuevoMetodoPago, @RequestParam double comision){
        MetodoPago resultado = repo.crearMetodoPago(nuevoMetodoPago, comision);
        return ResponseEntity.ok(resultado);
    }

   @PutMapping("/actualizar/{id}")
public ResponseEntity<MetodoPago> actualizarMetodoPago(
        @PathVariable int id,
        @RequestParam String nombre,
        @RequestParam double comision) {

    MetodoPago resultado = repo.actualizarMetodosPago(
            new MetodoPago(id, nombre, comision)
    );

    if (resultado == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(resultado);
}
    @DeleteMapping("/eliminar/{id}")
public ResponseEntity<Void> eliminarMetodoPago(@PathVariable int id) {

    boolean eliminado = repo.eliminarMetodoPago(id);

    if (!eliminado) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.noContent().build(); // 204
}

    

}
