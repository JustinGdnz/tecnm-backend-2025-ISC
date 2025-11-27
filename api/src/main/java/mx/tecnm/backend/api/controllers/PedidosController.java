package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Pedidos;
import mx.tecnm.backend.api.repository.PedidosDAO;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    PedidosDAO repo;

    @GetMapping()
    public ResponseEntity<List<Pedidos>> obtenerPedidos() {
        List<Pedidos> resultado = repo.consultarPedidos();
        return ResponseEntity.ok(resultado);
    }

}
