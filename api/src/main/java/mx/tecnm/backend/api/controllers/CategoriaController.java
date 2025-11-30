package mx.tecnm.backend.api.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        List<Categoria> resultado = repo.consultarCategorias();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        Categoria categoria = repo.obtenerCategoriaPorId(id);
        return categoria == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestParam String nombre) {
        Categoria nueva = new Categoria(0, nombre); // ID temporal para memoria
        Categoria creada = repo.crearCategoria(nueva);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable int id,
            @RequestParam String nombre) {

        Categoria actualizada = new Categoria(id, nombre);
        Categoria resultado = repo.actualizarCategoria(id, actualizada);

        return resultado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {

        boolean eliminado = repo.eliminarCategoria(id);

        return eliminado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}