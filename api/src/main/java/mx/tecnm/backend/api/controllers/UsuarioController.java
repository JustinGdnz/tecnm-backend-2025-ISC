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
import mx.tecnm.backend.api.dto.UsuarioRequest;
import mx.tecnm.backend.api.models.Usuario;
import mx.tecnm.backend.api.repository.UsuarioDAO;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioDAO repo;

    @Operation(summary = "Obtener una lista de todos los usuarios.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente.")
        }
    )
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> resultado = repo.consultarUsuarios();
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Obtener un usuario por su ID.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente."),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado.", content = @io.swagger.v3.oas.annotations.media.Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario usuario = repo.consultarUsuarioPorId(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Crear un usuario nuevo.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente.")
        }
    )
    @PostMapping()
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody UsuarioRequest usuario) {
        Usuario nuevoUsuario = repo.crearUsuario(
            usuario.nombre(),
            usuario.email(),
            usuario.telefono(),
            usuario.sexo(),
            usuario.fechaNacimiento(),
            usuario.contrasena()
        );

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(nuevoUsuario.id())
            .toUri();

        return ResponseEntity.created(location).body(nuevoUsuario);
    }

    @Operation(summary = "Actualizar un usuario.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente."),
            @ApiResponse(responseCode = "404", description = "El usuario no existe.", content = @io.swagger.v3.oas.annotations.media.Content)
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable int id, @Valid @RequestBody UsuarioRequest usuario) {
        Usuario existente = repo.consultarUsuarioPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        Usuario actualizado = repo.actualizarUsuario(
                new Usuario(id, usuario.nombre(), usuario.email(), usuario.telefono(),
                        usuario.sexo(), usuario.fechaNacimiento(), usuario.contrasena(),
                        existente.fecha_registro()));

        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar un usuario.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente.")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) {

        repo.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
