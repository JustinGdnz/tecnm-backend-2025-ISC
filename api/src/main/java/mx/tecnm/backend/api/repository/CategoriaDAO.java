package mx.tecnm.backend.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Categoria;

@Repository
public class CategoriaDAO {

    private List<Categoria> categorias = new ArrayList<>();
    private int nextId = 1;

    public List<Categoria> consultarCategorias() {
        return categorias;
    }

    public Categoria obtenerCategoriaPorId(int id) {
        return categorias.stream()
            .filter(c -> c.id() == id)
            .findFirst()
            .orElse(null);
    }

    public Categoria crearCategoria(Categoria nueva) {
        Categoria conId = new Categoria(nextId++, nueva.nombre());
        categorias.add(conId);
        return conId;
    }

    public Categoria actualizarCategoria(int id, Categoria actualizada) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).id() == id) {
                categorias.set(i, actualizada);
                return actualizada;
            }
        }
        return null;
    }

    public boolean eliminarCategoria(int id) {
        return categorias.removeIf(c -> c.id() == id);
    }
}
