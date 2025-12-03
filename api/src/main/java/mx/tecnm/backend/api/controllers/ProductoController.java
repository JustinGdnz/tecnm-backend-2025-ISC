package mx.tecnm.backend.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mx.tecnm.backend.api.dto.ProductoRequest;
import mx.tecnm.backend.api.models.Producto;
import mx.tecnm.backend.api.repository.ProductoDAO;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    ProductoDAO repo;

    @Operation(summary = "Obtener una lista de todos los productos.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente.")
        }
    )
    @GetMapping()
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> resultado = repo.consultarProductos();
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Obtener un producto por su ID.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente."),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado.", content = @io.swagger.v3.oas.annotations.media.Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = repo.consultarProductoPorId(id);

        if (producto == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Crear un nuevo producto.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente.")
        }
    )
    @PostMapping()
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody ProductoRequest producto) {
        Producto nuevoProducto = repo.crearProducto(new Producto(
                0,
                producto.nombre(),
                producto.descripcion(),
                producto.marca(),
                producto.color(),
                producto.precio(),
                producto.peso(),
                producto.alto(),
                producto.ancho(),
                producto.profundidad(),
                producto.categoriasID(),
                producto.sku()
        ));

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(nuevoProducto.id())
            .toUri();

        return ResponseEntity.created(location).body(nuevoProducto);
    }

    @Operation(summary = "Actualizar un producto.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado.", content = @io.swagger.v3.oas.annotations.media.Content)
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id, @Valid @RequestBody ProductoRequest producto) {

        Producto existe = repo.consultarProductoPorId(id);

        if (existe == null)
            return ResponseEntity.notFound().build();

        Producto actualizado = repo.actualizarProducto(
                new Producto(
                        id,
                        producto.nombre(),
                        producto.descripcion(),
                        producto.marca(),
                        producto.color(),
                        producto.precio(),
                        producto.peso(),
                        producto.alto(),
                        producto.ancho(),
                        producto.profundidad(),
                        producto.categoriasID(),
                        producto.sku()
                )
        );

        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar un producto.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente.")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        repo.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
