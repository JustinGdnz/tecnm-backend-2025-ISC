package mx.tecnm.backend.api.controllers;
import mx.tecnm.backend.api.models.Producto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class Test {
    
    @GetMapping("/hello")
    public String helloworld()
    {
        return "Hola API Rest";
    }

    @GetMapping("/producto")
    public Producto GetProducto()
    {
        Producto p = new Producto();
        p.nombre = "CocaCola";
        p.precio = 20.0;
        p.codigoBarras = "17853467344";
        return p;
    }

    @GetMapping("/productos")
    public Producto[] GetProductos()
    {
        Producto p1 = new Producto();
        p1.nombre = "CocaCola";
        p1.precio = 20.0;
        p1.codigoBarras = "12905890235";

        Producto p2= new Producto();
        p2.nombre = "Pepsi";
        p2.precio = 2.0;
        p2.codigoBarras = "125436wq34yrh";

        Producto p3 = new Producto();
        p3.nombre = "Fanta";
        p3.precio = 15.0;
        p3.codigoBarras = "w9rghy9prygsd";

        Producto[] ps = {p1, p2, p3};
        return ps;
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id)
    {        
        if (id < 0 || id > 2)
        {
            return ResponseEntity.notFound().build();
        }

        Producto[] productos = new Producto[3];
        Producto p1 = new Producto();
        p1.nombre = "CocaCola";
        p1.precio = 20.0;
        p1.codigoBarras = "12905890235";
        productos[0] = p1;

        Producto p2= new Producto();
        p2.nombre = "Pepsi";
        p2.precio = 2.0;
        p2.codigoBarras = "125436wq34yrh";
        productos[1] = p2;

        Producto p3 = new Producto();
        p3.nombre = "Fanta";
        p3.precio = 15.0;
        p3.codigoBarras = "w9rghy9prygsd";
        productos[2] = p3;

        Producto resultado = productos[id];

        return ResponseEntity.ok(resultado);
        
    }
}
