package viewnext.practica4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Series;
import viewnext.practica4.entitiesDTOs.SeriesDto;
import viewnext.practica4.servicesImp.SeriesServiceImp;

import java.util.List;

/**
 * Controlador REST para la entidad Series.
 */
@RestController
@RequestMapping("/series") // Ruta base para las operaciones relacionadas con series
public class SeriesController {

    @Autowired
    private SeriesServiceImp seriesService; // Servicio con la lógica de negocio para series

    /**
     * Obtiene una lista de todas las series.
     *
     * @return ResponseEntity con la lista de series
     */
    @GetMapping
    public ResponseEntity<List<SeriesDto>> listarSeries() {
        List<SeriesDto> lista = seriesService.listarSeries();
        return ResponseEntity.ok(lista);
    }

    /**
     * Crea una nueva serie.
     *
     * @param serieDto
     *         Objeto Series con los datos de la nueva serie
     * @return ResponseEntity con la serie creada en formato DTO
     */
    @PostMapping
    public ResponseEntity<SeriesDto> crearSerie(@RequestBody SeriesDto serieDto) {
        Series nueva = seriesService.anadirSeries(serieDto);
        SeriesDto dto = seriesService.mapToDto(nueva);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Modifica una serie existente.
     *
     * @param serieDto
     *         Objeto Series con los datos actualizados
     * @return ResponseEntity con la serie modificada en formato DTO, o 404 si no se encuentra
     */
    @PutMapping
    public ResponseEntity<SeriesDto> modificarSerie(@RequestBody SeriesDto serieDto) {
        try {
            Series modificada = seriesService.modificarSeries(serieDto);
            SeriesDto dto = seriesService.mapToDto(modificada);
            return ResponseEntity.ok(dto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Borrar una serie por su ID.
     *
     * @param idSeries
     *         ID de la serie a borrar
     * @return ResponseEntity con estado 204 si la operación fue exitosa
     */
    @DeleteMapping("/{idSeries}")
    public ResponseEntity<Void> borrarSerie(@PathVariable Long idSeries) {
        seriesService.borrarSeries(idSeries);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar una serie utilizando filtros opcionales como título, año min, añño max, director, productora.
     *
     * @param titulo
     *         the titulo
     * @param anioMin
     *         the anio min
     * @param anioMax
     *         the anio max
     * @param director
     *         the director
     * @param productora
     *         the productora
     * @return the response entity
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<SeriesDto>> buscarConFiltros(@RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer anioMin, @RequestParam(required = false) Integer anioMax,
            @RequestParam(required = false) String director, @RequestParam(required = false) String productora) {

        List<SeriesDto> filtradas = seriesService.buscarSeriesConFiltros(titulo, anioMin, anioMax, director,
                productora);
        return ResponseEntity.ok(filtradas);
    }
}
