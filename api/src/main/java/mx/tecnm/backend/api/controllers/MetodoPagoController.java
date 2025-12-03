package mx.tecnm.backend.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mx.tecnm.backend.api.dto.MetodoPagoRequest;
import mx.tecnm.backend.api.models.MetodoPago;
import mx.tecnm.backend.api.repository.MetodoPagoDAO;

@RestController
@RequestMapping("/api/v1/metodos-pago")
public class MetodoPagoController {

    @Autowired
    MetodoPagoDAO repo;

    @Operation(summary = "Obtener una lista de todos los métodos de pago.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Lista de métodos de pago obtenida exitosamente.")
        }
    )
    @GetMapping()
    public ResponseEntity<List<MetodoPago>> obtenerMetodoPago() {
        List<MetodoPago> resultado = repo.consultarMetodoPago();
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Obtener un método de pago por su ID.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Método de pago obtenido exitosamente."),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado.", content = @io.swagger.v3.oas.annotations.media.Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> obtenerMetodoPagoPorId(@PathVariable int id){
        MetodoPago resultado = repo.consultarMetodoPagoPorId(id);

        if(resultado == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultado);
        }
    }

    @Operation(summary = "Crear un nuevo método de pago.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Método de pago creado exitosamente.")
        }
    )
    @PostMapping()
    public ResponseEntity<MetodoPago> crearMetodoPago(@Valid @RequestBody MetodoPagoRequest metodoPago) {
        MetodoPago nuevoMetodoPago = repo.crearMetodoPago(metodoPago.nombre(), metodoPago.comision());

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(nuevoMetodoPago.id())
            .toUri();

        return ResponseEntity.created(location).body(nuevoMetodoPago);
    }

    @Operation(summary = "Actualizar un método de pago.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Método de pago actualizado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado.", content = @io.swagger.v3.oas.annotations.media.Content)
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> actualizarMetodoPago(@PathVariable int id, @Valid @RequestBody MetodoPagoRequest metodoPago) {

        MetodoPago resultado = repo.actualizarMetodosPago(
                new MetodoPago(id, metodoPago.nombre(), metodoPago.comision())
        );

        if (resultado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Eliminar un método de pago.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Método de pago eliminado exitosamente.")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMetodoPago(@PathVariable int id) {
        repo.eliminarMetodoPago(id);

        return ResponseEntity.noContent().build(); // 204
    }
}
