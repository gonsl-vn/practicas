package viewnext.practica4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Peliculas;
import viewnext.practica4.entitiesDTOs.PeliculasDto;
import viewnext.practica4.servicesImp.PeliculasServiceImp;

import java.util.List;

/**
 * Controlador REST para la entidad Peliculas.
 */
@RestController
@RequestMapping("/peliculas") // Ruta base para las operaciones relacionadas con películas
public class PeliculasController {

    @Autowired
    private PeliculasServiceImp peliculasServiceImp; // Servicio con la lógica de negocio para peliculas

    /**
     * Obtiene una lista de todas las películas.
     *
     * @return ResponseEntity con la lista de películas
     */
    @GetMapping
    public ResponseEntity<List<PeliculasDto>> listarPeliculas() {
        List<PeliculasDto> peliculas = peliculasServiceImp.listarPeliculas();
        return ResponseEntity.ok(peliculas);
    }

    /**
     * Añade una nueva película.
     *
     * @param peliculas
     *         Objeto Películas con los datos de la nueva pelicula
     * @return ResponseEntity con la película creada en formato DTO
     */
    @PostMapping
    public ResponseEntity<PeliculasDto> anadirPelicula(@RequestBody Peliculas peliculas) {
        Peliculas nuevaPelicula = peliculasServiceImp.anadirPelicula(peliculas);
        PeliculasDto peliculasDto = peliculasServiceImp.mapToDto(nuevaPelicula);
        return new ResponseEntity<>(peliculasDto, HttpStatus.CREATED);
    }

    /**
     * Modificar una película existente.
     *
     * @param peliculas
     *         Objeto de películas con los datos actualizados
     * @return ResponseEntity con la película en formato DTO, o error si no se encuentra
     */
    @PutMapping
    public ResponseEntity<PeliculasDto> modificarPeliculas(@RequestBody Peliculas peliculas) {
        try {
            Peliculas peliculaModificado = peliculasServiceImp.modificarPeliculas(peliculas);
            PeliculasDto peliculasDto = peliculasServiceImp.mapToDto(peliculaModificado);
            return ResponseEntity.ok(peliculasDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Borrar una película por su ID.
     *
     * @param idPelicula
     *         ID de la película a borrar
     * @return ResponseEntity con estado 204 si la operación fue exitosa
     */
    @DeleteMapping("/{idPelicula}")
    public ResponseEntity<Void> borrarPeliculas(Long idPelicula) {
        peliculasServiceImp.borrarPeliculas(idPelicula);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar películas con filtros opcionales como título, año min, año max, productora, director.
     *
     * @param titulo
     *         Título de la película
     * @param anioMin
     *         the anio min
     * @param anioMax
     *         the anio max
     * @param productora
     *         the productora
     * @param director
     *         the director
     * @return the response entity
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<PeliculasDto>> buscarPeliculasConFiltros(@RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer anioMin, @RequestParam(required = false) Integer anioMax,
            @RequestParam(required = false) String productora, @RequestParam(required = false) String director) {

        List<PeliculasDto> peliculasFiltradas = peliculasServiceImp.buscarPeliculasConFiltros(titulo, anioMin, anioMax,
                productora, director);

        return ResponseEntity.ok(peliculasFiltradas);
    }

}
