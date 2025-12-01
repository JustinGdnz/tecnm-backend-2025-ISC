package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.tecnm.backend.api.models.Categoria;
import mx.tecnm.backend.api.repository.CategoriaDAO;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaDAO repo;

    // ------------------ GET ALL ----------------------
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> resultado = repo.consultarCategorias();
        return ResponseEntity.ok(resultado);
    }

    // ------------------ GET BY ID ----------------------
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        Categoria categoria = repo.consultarCategoriaPorId(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria);
    }

    // ------------------ POST (Crear) ----------------------
    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria(@RequestParam String nombre) {

        Categoria categoria = repo.crearCategoria(nombre);

        return ResponseEntity.ok(categoria);
    }

    // ------------------ PUT (Actualizar) ----------------------
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable int id,
            @RequestParam String nombre) {

        Categoria existente = repo.consultarCategoriaPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        Categoria actualizado = repo.actualizarCategoria(id, nombre);

        return ResponseEntity.ok(actualizado);
    }

    // ------------------ DELETE ----------------------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {

        boolean eliminado = repo.eliminarCategoria(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
