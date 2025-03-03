package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.ProductoraService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ProductoraModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ProductoraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productora")
public class ProductoraController {
    @Autowired
    private ProductoraService productoraService;

    @GetMapping
    public List<ProductoraEntity> listarProductoras(){
        return productoraService.listarProductoras();
    }
    @PostMapping("/post")
    public ResponseEntity<ProductoraModel> crearProductora(@RequestBody ProductoraModel productora){
        return ResponseEntity.ok(productoraService.addProductora(productora));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductoraModel> eliminarProductora(
            @PathVariable Integer id){
        return ResponseEntity.ok(productoraService.deleteProductora(id));
    }
    @PutMapping("/put")
    public ResponseEntity<ProductoraModel> actualizarProductora(
            @RequestBody ProductoraModel productora){
        return ResponseEntity.ok(productoraService.updateProductora(productora));
    }
}
