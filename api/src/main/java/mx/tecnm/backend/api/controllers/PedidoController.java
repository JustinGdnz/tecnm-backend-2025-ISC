package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Pedido;
import mx.tecnm.backend.api.repository.PedidoDAO;

@RestController
@RequestMapping("/usuarios/{usuario_id}/pedidos")
public class PedidoController {

    @Autowired
    PedidoDAO repo;

    @GetMapping()
    public ResponseEntity<List<Pedido>> obtenerPedidos(@PathVariable int usuario_id) {
        List<Pedido> resultado = repo.consultarPedidos(usuario_id);
        return ResponseEntity.ok(resultado);
    }

}
