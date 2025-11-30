package mx.tecnm.backend.api.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Categoria;
import mx.tecnm.backend.api.repository.CategoriaDAO;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaDAO repo;

    @GetMapping()
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> resultado = repo.conusultarCategorias();
        return ResponseEntity.ok(resultado);
    }
@GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        return ResponseEntity.ofNullable(repo.consultarCategoriaPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria(@RequestParam String nombre,
                                                    @RequestParam String descripcion) {
        return ResponseEntity.ok(repo.crearCategoria(nombre, descripcion));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable int id,
                                                         @RequestParam String nombre,
                                                         @RequestParam String descripcion) {

        var existente = repo.consultarCategoriaPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        var actualizado = repo.actualizarCategoria(
                new Categoria(id, nombre, descripcion, existente.getFechaRegistro())
        );

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        return repo.eliminarCategoria(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}