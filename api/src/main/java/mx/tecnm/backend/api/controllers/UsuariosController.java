package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Usuarios;
import mx.tecnm.backend.api.repository.UsuariosDAO;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    UsuariosDAO repo;

    @GetMapping()
    public ResponseEntity<List<Usuarios>> obtenerUsuarios() {
        List<Usuarios> resultado = repo.consultarUsuarios();
        return ResponseEntity.ok(resultado);
    }

}
