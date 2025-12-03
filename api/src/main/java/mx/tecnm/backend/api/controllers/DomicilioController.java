package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Domicilio;
import mx.tecnm.backend.api.repository.DomicilioDAO;

@RestController
@RequestMapping("/api/v1/domicilios")
public class DomicilioController {

    @Autowired
    DomicilioDAO repo;

    @GetMapping()
    public ResponseEntity<List<Domicilio>> obtenerDomicilios() {
        List<Domicilio> resultado = repo.consultarDomicilios();
        return ResponseEntity.ok(resultado);
    }

}

