package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.MetodosPago;
import mx.tecnm.backend.api.repository.MetodosPagoDAO;

@RestController
@RequestMapping("/metodospago")
public class MetodosPagoController {

    @Autowired
    MetodosPagoDAO repo;

    @GetMapping()
    public ResponseEntity<List<MetodosPago>> obtenerMetodosPago() {
        List<MetodosPago> resultado = repo.consultarMetodosPago();
        return ResponseEntity.ok(resultado);
    }
    

}
