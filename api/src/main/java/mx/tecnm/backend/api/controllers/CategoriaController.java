package mx.tecnm.backend.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mx.tecnm.backend.api.dto.CategoriaRequest;
import mx.tecnm.backend.api.models.Categoria;
import mx.tecnm.backend.api.repository.CategoriaDAO;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    CategoriaDAO repo;

    // ------------------ GET ALL ----------------------
    @Operation(summary = "Obtener una lista de todas las categorías.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente.")
        }
    )
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> resultado = repo.consultarCategorias();
        return ResponseEntity.ok(resultado);
    }

    // ------------------ GET BY ID ----------------------
    @Operation(summary = "Obtener una categoria por su ID.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Categoría obtenida exitosamente."),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada.", content = @io.swagger.v3.oas.annotations.media.Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        Categoria categoria = repo.consultarCategoriaPorId(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria);
    }

    // ------------------ POST (Crear) ----------------------
    @Operation(summary = "Crear una nueva categoría.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente.")
        }
    )
    @PostMapping()
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody CategoriaRequest categoria) {

        Categoria nuevaCategoria = repo.crearCategoria(categoria.nombre());

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(nuevaCategoria.id())
            .toUri();

        return ResponseEntity.created(location).body(nuevaCategoria);
    }

    // ------------------ PUT (Actualizar) ----------------------
    @Operation(summary = "Actualizar una categoría.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente."),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada.", content = @io.swagger.v3.oas.annotations.media.Content)
        }
    )   
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable int id,
            @Valid @RequestBody CategoriaRequest categoria) {

        Categoria existente = repo.consultarCategoriaPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        Categoria actualizado = repo.actualizarCategoria(id, categoria.nombre());

        return ResponseEntity.ok(actualizado);
    }

    // ------------------ DELETE ----------------------
    @Operation(summary = "Eliminar una categoría.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente.")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        repo.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
