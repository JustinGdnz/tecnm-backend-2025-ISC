package mx.tecnm.backend.api.controllers;

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


import mx.tecnm.backend.api.models.Producto;
import mx.tecnm.backend.api.repository.ProductoDAO;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    ProductoDAO repo;

    @GetMapping()
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> resultado = repo.consultarProductos();
        return ResponseEntity.ok(resultado);
    }
@GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = repo.consultarProductoPorId(id);

        if (producto == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(producto);
    }

    @PostMapping("/crear")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto p) {
        Producto creado = repo.crearProducto(p);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id, @RequestBody Producto p) {

        Producto existe = repo.consultarProductoPorId(id);

        if (existe == null)
            return ResponseEntity.notFound().build();

        Producto actualizado = repo.actualizarProducto(
                new Producto(
                        id,
                        p.nombre(),
                        p.descripcion(),
                        p.marca(),
                        p.color(),
                        p.precio(),
                        p.peso(),
                        p.alto(),
                        p.ancho(),
                        p.profundidad(),
                        p.categorias_id(),
                        p.sku()
                )
        );

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {

        boolean eliminado = repo.eliminarProducto(id);

        if (!eliminado)
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }
}
