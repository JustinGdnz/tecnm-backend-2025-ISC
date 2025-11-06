package mx.tecnm.backend.api.controllers;
import mx.tecnm.backend.api.models.Producto;

import org.springframework.web.bind.annotation.GetMapping;
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
        p1.nombre = "No se wey";
        p1.precio = 20.0;
        p1.codigoBarras = "12905890235";

        Producto p2= new Producto();
        p2.nombre = "Koka";
        p2.precio = 2.0;
        p2.codigoBarras = "125436wq34yrh";

        Producto p3 = new Producto();
        p3.nombre = "jokey--";
        p3.precio = 15.0;
        p3.codigoBarras = "w9rghy9prygsd";

        Producto[] ps = {p1, p2, p3};
        return ps;
    }
}
