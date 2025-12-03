package mx.tecnm.backend.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mx.tecnm.backend.api.dto.CheckoutRequest;
import mx.tecnm.backend.api.dto.DetalleCarritoRequest;
import mx.tecnm.backend.api.models.DetalleCarrito;
import mx.tecnm.backend.api.repository.DetalleCarritoDAO;

@RestController
@RequestMapping("/api/v1/usuarios/{usuario_id}/carrito")
public class DetalleCarritoController {

    @Autowired
    DetalleCarritoDAO repo;

    @Operation(summary = "Obtener el carrito de compras de un usuario.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Carrito de compras obtenido exitosamente.")
        }
    )
    @GetMapping
    ResponseEntity<List<DetalleCarrito>> obtenerCarrito(@PathVariable int usuario_id)
    {
        List<DetalleCarrito> resultado = repo.obtenerCarrito(usuario_id);
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Agregar un producto al carrito de compras de un usuario.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Producto agregado al carrito exitosamente.")
        }
    )
    @PostMapping
    ResponseEntity<DetalleCarrito> agregarAlCarrito(@PathVariable int usuario_id, @Valid @RequestBody DetalleCarritoRequest request)
    {
        DetalleCarrito busqueda = repo.buscarProductoEnCarrito(usuario_id, request.productoID());
        DetalleCarrito resultado;
        if (busqueda == null)
        {
            resultado = repo.agregarAlCarrito(usuario_id, request.productoID(), request.cantidad());
        }
        else
        {
            resultado = repo.actualizarCantidadEnCarrito(busqueda.id(), busqueda.cantidad() + request.cantidad());
        }

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(resultado.id())
            .toUri();

        return ResponseEntity.created(location).body(resultado);
    }

    @Operation(summary = "Borrar un producto del carrito de compras de un usuario.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Producto borrado del carrito exitosamente.")
        }
    )
    @DeleteMapping()
    ResponseEntity<Void> borrarDelCarrito(@PathVariable int usuario_id, @Valid @RequestBody DetalleCarritoRequest request)
    {
        repo.borrarDelCarrito(usuario_id, request.productoID(), request.cantidad());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Realizar el pedido del carrito de compras de un usuario.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Pedido realizado exitosamente."),
            @ApiResponse(responseCode = "400", description = "No se pudo realizar el pedido.")
        }
    )
    @PostMapping("/checkout")
    ResponseEntity<Void> checkoutCarrito(@PathVariable int usuario_id, @RequestBody CheckoutRequest request)
    {
        boolean exito = repo.checkoutCarrito(usuario_id, request.metodoPagoID());
        
        if (!exito)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.created(null).build();
    }
}
