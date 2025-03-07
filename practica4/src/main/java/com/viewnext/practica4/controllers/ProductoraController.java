package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.services.ProductoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productora")
public class ProductoraController {

    private final ProductoraService productoraService;

    public ProductoraController(ProductoraService productoraService) {
        this.productoraService = productoraService;
    }

    @GetMapping
    public ResponseEntity<List<Productora>> obtenerProductoraes() {
        return ResponseEntity.ok(productoraService.obtenerProductoras());
    }

    @GetMapping("/{idProductora}")
    public ResponseEntity<Productora> obtenerProductoraes(@PathVariable int idProductora) {
        return ResponseEntity.ok(productoraService.obtenerProductoraPorId(idProductora).get());
    }

    @PostMapping
    public ResponseEntity<Productora> insertarProductora(@RequestBody Productora productora) {
        productoraService.insertarProductora(productora);
        return ResponseEntity.ok(productora);
    }

    @PutMapping("/{idProductora}")
    public ResponseEntity<Productora> ActualizarProductora(@PathVariable int idProductora,
            @RequestBody Productora productora) {
        return ResponseEntity.ok(productoraService.actualizarProductora(idProductora, productora));
    }

    @DeleteMapping("/{idProductora}")
    public ResponseEntity<Void> BorrarProductora(@PathVariable int idProductora) {
        productoraService.eliminarProductora(idProductora);
        return ResponseEntity.ok().build();
    }
}
