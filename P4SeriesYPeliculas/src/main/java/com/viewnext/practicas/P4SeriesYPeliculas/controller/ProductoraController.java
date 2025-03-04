package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.ProductoraService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ProductoraModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ProductoraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/buscaPorParametros")
    public ResponseEntity<Page<ProductoraEntity>> buscarPorParametros(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer foundedInYear,
            @RequestParam(required = false) String peliTitle,
            @RequestParam(required = false) String serieTitle,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ){
        Sort sort = order.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(productoraService.buscarProductoraPorParametros(name,
                foundedInYear, peliTitle, serieTitle, pageable));
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
