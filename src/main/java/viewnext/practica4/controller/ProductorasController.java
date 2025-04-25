package viewnext.practica4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Productoras;
import viewnext.practica4.entitiesDTOs.ProductorasDto;
import viewnext.practica4.servicesImp.ProductorasServiceImp;

import java.util.List;

/**
 * Controlador REST para la entidad Productora.
 */
@RestController
@RequestMapping("/productoras") // Ruta base de las operaciones relacionadas con las productoras
public class ProductorasController {

    @Autowired
    private ProductorasServiceImp productorasService; // Servicio con la lógica de negocio para productoras

    /**
     * Obtiene una lista de todas las productoras.
     *
     * @return ResponseEntity con la lista de productoras
     */
    @GetMapping
    public ResponseEntity<List<ProductorasDto>> listarProductoras() {
        List<ProductorasDto> lista = productorasService.listarProductoras();
        return ResponseEntity.ok(lista);
    }

    /**
     * Guardar una nueva productora.
     *
     * @param productora
     *         Objeto Productoras con los datos de la nueva productora
     * @return ResponseEntity con la productora creada en formato DTO
     */
    @PostMapping
    public ResponseEntity<ProductorasDto> guardarProductora(@RequestBody Productoras productora) {
        Productoras nueva = productorasService.anadirProductoras(productora);
        ProductorasDto dto = productorasService.mapToDto(nueva);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Modificar una productora existente.
     *
     * @param productora
     *         Objeto Productoras con los datos actualizados
     * @return ResponseEntity con la productora modificada en formato DTO, o 404 si no es encontrada
     */
    @PutMapping
    public ResponseEntity<ProductorasDto> modificarProductora(@RequestBody Productoras productora) {
        try {
            Productoras modificada = productorasService.modificarProductoras(productora);
            ProductorasDto dto = productorasService.mapToDto(modificada);
            return ResponseEntity.ok(dto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Borrar productora por su ID.
     *
     * @param idProductora
     *         ID de la productora a borrar
     * @return ResponseEntity con estado 204 si la operación fue exitosa
     */
    @DeleteMapping("/{idProductora}")
    public ResponseEntity<Void> borrarProductora(@PathVariable Long idProductora) {
        productorasService.borrarProductoras(idProductora);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar productoras utilizando filtros opcionales como nombre, año min, año max.
     *
     * @param nombre
     *         the nombre
     * @param anioMin
     *         the anio min
     * @param anioMax
     *         the anio max
     * @return the response entity
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<ProductorasDto>> buscarConFiltros(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer anioMin, @RequestParam(required = false) Integer anioMax) {

        List<ProductorasDto> filtradas = productorasService.buscarProductorasConFiltros(nombre, anioMin, anioMax);
        return ResponseEntity.ok(filtradas);
    }
}
