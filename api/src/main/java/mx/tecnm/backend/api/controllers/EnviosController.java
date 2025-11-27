package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Envios;
import mx.tecnm.backend.api.repository.EnviosDAO;

@RestController
@RequestMapping("/envios")
public class EnviosController {

      @Autowired
    EnviosDAO repo;

    @GetMapping()
    public ResponseEntity<List<Envios>> obtenerEnvios() {
        List<Envios> resultado = repo.consultarEnvios();
        return ResponseEntity.ok(resultado);
    }

}
