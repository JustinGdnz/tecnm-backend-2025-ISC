package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.DetallesPedido;
import mx.tecnm.backend.api.repository.DetallesPedidoDAO;

@RestController
@RequestMapping("/detallespedido")
public class DetallesPedidoController {

    @Autowired
    DetallesPedidoDAO repo;

    @GetMapping()
    public ResponseEntity<List<DetallesPedido>> obtenerDetallesPedido() {
        List<DetallesPedido> resultado = repo.obtenerDetallesPedido();
        return ResponseEntity.ok(resultado);
    }
}

