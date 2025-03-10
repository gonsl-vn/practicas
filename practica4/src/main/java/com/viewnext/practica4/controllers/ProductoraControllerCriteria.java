package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.services.ProductoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criteria/productoras")
public class ProductoraControllerCriteria {

    private final ProductoraService productoraServiceCriteria;

    public ProductoraControllerCriteria(ProductoraService productoraServiceCriteria) {
        this.productoraServiceCriteria = productoraServiceCriteria;
    }

    /**
     * Inserta una nueva productora usando Criteria API.
     */
    @PostMapping
    public ResponseEntity<String> insertarProductora(@RequestBody Productora productora) {
        productoraServiceCriteria.insertarProductoraCriteria(productora);
        return ResponseEntity.ok("Productora insertada correctamente.");
    }

    /**
     * Obtiene la lista de todas las productoras usando Criteria API.
     */
    @GetMapping
    public ResponseEntity<List<Productora>> listarProductoras() {
        return ResponseEntity.ok(productoraServiceCriteria.obtenerProductorasCriteria());
    }

    /**
     * Busca una productora por ID usando Criteria API.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Productora> buscarProductora(@PathVariable int id) {
        Productora productora = productoraServiceCriteria.obtenerProductoraPorIdCriteria(id);
        return ResponseEntity.ok(productora);
    }

    /**
     * Actualiza una productora por ID usando Criteria API.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarProductora(@PathVariable int id, @RequestBody Productora productoraNueva) {
        productoraServiceCriteria.actualizarProductoraCriteria(id, productoraNueva);
        return ResponseEntity.ok("Productora actualizada correctamente.");
    }

    /**
     * Elimina una productora por ID usando Criteria API.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarProductora(@PathVariable int id) {
        productoraServiceCriteria.eliminarProductoraCriteria(id);
        return ResponseEntity.ok("Productora eliminada correctamente.");
    }
}
