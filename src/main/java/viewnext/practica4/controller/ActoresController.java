package viewnext.practica4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Actores;
import viewnext.practica4.entitiesDTOs.ActoresDto;
import viewnext.practica4.servicesImp.ActoresServiceImp;

import java.util.List;

/**
 * Controlador REST para la entidad Actores.
 */
@RestController
@RequestMapping("/actores") // Ruta base para las operaciones relacionadas con actores
public class ActoresController {

    @Autowired
    private ActoresServiceImp actoresService; // Servicio que contiene la lógica de negocio para actores

    /**
     * Obtiene una lista de todos los actores.
     *
     * @return ResponseEntity con la lista de actores
     */
    @GetMapping
    public ResponseEntity<List<ActoresDto>> listarActores() {
        List<ActoresDto> actores = actoresService.listarActores();
        return ResponseEntity.ok(actores);
    }

    /**
     * Guarda un nuevo actor.
     *
     * @param actor
     *         Actor a guardar
     * @return ResponseEntity con el actor guardado en formato DTO
     */
    @PostMapping
    public ResponseEntity<ActoresDto> guardarActor(@RequestBody Actores actor) {
        Actores nuevoActor = actoresService.guardarActores(actor);
        ActoresDto actorDto = actoresService.mapToDto(nuevoActor);
        return new ResponseEntity<>(actorDto, HttpStatus.CREATED);
    }

    /**
     * Modifica un actor existente.
     *
     * @param actor
     *         Actor con los datos actualizados
     * @return ResponseEntity con el actor modificado en formato DTO, o 404 si no se encuentra
     */
    @PutMapping
    public ResponseEntity<ActoresDto> modificarActor(@RequestBody Actores actor) {
        try {
            Actores actorModificado = actoresService.modificarActores(actor);
            ActoresDto actorDto = actoresService.mapToDto(actorModificado);
            return ResponseEntity.ok(actorDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Elimina un actor por su ID.
     *
     * @param idActor
     *         ID del actor a eliminar
     * @return ResponseEntity con estado 204 si la operación fue exitosa
     */
    @DeleteMapping("/{idActor}")
    public ResponseEntity<Void> borrarActor(@PathVariable String idActor) {
        actoresService.borrarActores(idActor);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca actores utilizando filtros opcionales como nombre, nacionalidad y rangos de edad.
     *
     * @param nombre
     *         Nombre del actor (opcional)
     * @param nacionalidad
     *         Nacionalidad del actor (opcional)
     * @param edadMin
     *         Edad mínima (opcional)
     * @param edadMax
     *         Edad máxima (opcional)
     * @return ResponseEntity con la lista de actores que coinciden con los filtros
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<ActoresDto>> buscarActoresConFiltros(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) String nacionalidad, @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax) {
        List<ActoresDto> actoresFiltrados = actoresService.buscarActoresConFiltros(nombre, nacionalidad, edadMin,
                edadMax);
        return ResponseEntity.ok(actoresFiltrados);
    }
}
