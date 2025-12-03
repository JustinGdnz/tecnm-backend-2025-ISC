package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mx.tecnm.backend.api.models.DetallePedido;
import mx.tecnm.backend.api.repository.DetallePedidoDAO;

@RestController
@RequestMapping("/api/v1/usuarios/{usuario_id}/pedidos/{pedido_id}/detalles")
public class DetallePedidoController {

    @Autowired
    DetallePedidoDAO repo;

    @Operation(summary = "Obtener los detalles de un pedido específico de un usuario.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Detalles del pedido obtenidos exitosamente.")
        }
    )
    @GetMapping()
    public ResponseEntity<List<DetallePedido>> obtenerDetallesPedido(@PathVariable int usuario_id, @PathVariable int pedido_id) {
        List<DetallePedido> resultado = repo.obtenerDetallesPorPedidoUsuario(usuario_id, pedido_id);
        return ResponseEntity.ok(resultado);
    }
}

