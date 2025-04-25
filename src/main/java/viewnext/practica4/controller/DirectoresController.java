package viewnext.practica4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Directores;
import viewnext.practica4.entitiesDTOs.DirectoresDto;
import viewnext.practica4.servicesImp.DirectoresServiceImp;

import java.util.List;

/**
 * Controlador REST para la entidad Directores.
 */
@RestController
@RequestMapping("/directores") // Ruta base para las operaciones relacionadas con directores
public class DirectoresController {

    @Autowired
    private DirectoresServiceImp directoresServiceImp; // Servicio con la lógica de negocio para directores

    /**
     * Obtiene una lista de todos los directores.
     *
     * @return ResponseEntity con la lista de directores
     */
    @GetMapping
    public ResponseEntity<List<DirectoresDto>> listarDirectores() {
        List<DirectoresDto> directores = directoresServiceImp.listarDirectores();
        return ResponseEntity.ok(directores);
    }

    /**
     * Añade un nuevo director.
     *
     * @param directores
     *         Objeto Directores con los datos del nuevo director
     * @return ResponseEntity con el director creado en formato DTO
     */
    @PostMapping
    public ResponseEntity<DirectoresDto> anadirDirectores(@RequestBody Directores directores) {
        Directores nuevoDirector = directoresServiceImp.anadirDirectores(directores);
        DirectoresDto directoresDto = directoresServiceImp.mapToDto(nuevoDirector);
        return new ResponseEntity<>(directoresDto, HttpStatus.CREATED);
    }

    /**
     * Modifica un director existente.
     *
     * @param directores
     *         Objeto Directores con los datos actualizados
     * @return ResponseEntity con el director modificado en formato DTO, o 404 si no se encuentra
     */
    @PutMapping
    public ResponseEntity<DirectoresDto> modificarDirectores(@RequestBody Directores directores) {
        try {
            Directores directorModificado = directoresServiceImp.modificarDirectores(directores);
            DirectoresDto directoresDto = directoresServiceImp.mapToDto(directorModificado);
            return ResponseEntity.ok(directoresDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Elimina un director por su ID.
     *
     * @param idDirectores
     *         ID del director a eliminar
     * @return ResponseEntity con estado 204 si la operación fue exitosa
     */
    @DeleteMapping("/{idDirector}")
    public ResponseEntity<Void> eliminarDirectores(@RequestParam String idDirectores) {
        directoresServiceImp.eliminarDirectores(idDirectores);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca directores utilizando filtros opcionales como nombre, nacionalidad y rango de edad.
     *
     * @param nombre
     *         Nombre del director (opcional)
     * @param nacionalidad
     *         Nacionalidad del director (opcional)
     * @param edadMin
     *         Edad mínima (opcional)
     * @param edadMax
     *         Edad máxima (opcional)
     * @return ResponseEntity con la lista de directores que coinciden con los filtros
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<DirectoresDto>> buscarDirectoresConFiltros(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) String nacionalidad, @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax) {
        List<DirectoresDto> directoresFiltrados = directoresServiceImp.buscarDirectoresConFiltros(nombre, nacionalidad,
                edadMin, edadMax);
        return ResponseEntity.ok(directoresFiltrados);
    }
}
