package viewnext.practica4.servicesImp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Actores;
import viewnext.practica4.entitiesDTOs.ActoresDto;
import viewnext.practica4.repositories.ActoresRepository;
import viewnext.practica4.services.ActoresService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Actores service imp.
 */
@Service // Anotación de Spring que marca esta clase como un servicio
public class ActoresServiceImp implements ActoresService {

    @PersistenceContext
    private EntityManager entityManager; // Gestiona la persistencia (aunque aquí no se usa directamente en los métodos principales)

    @Autowired
    private ActoresRepository actoresRepository; // Inyección de dependencia del repositorio de Actores

    /**
     * Mapeo de Actores a DTO.
     *
     * @param actor
     *         El objeto Actor
     * @return El Actor DTO
     */
    public ActoresDto mapToDto(Actores actor) { // Método para convertir una entidad Actores a su DTO
        ActoresDto dto = new ActoresDto();
        dto.setIdActor(actor.getIdActor());
        dto.setNombre(actor.getNombre());
        dto.setApellido(actor.getApellido());
        dto.setEdad(actor.getEdad());
        dto.setNacionalidad(actor.getNacionalidad());

        if (actor.getPeliculas() != null) {
            Set<String> peliculasTitulos = actor.getPeliculas().stream().map(p -> p.getTitulo())
                    .collect(Collectors.toSet());
            dto.setPeliculasTitulos(peliculasTitulos);
        }

        if (actor.getSeries() != null) {
            Set<String> seriesTitulos = actor.getSeries().stream().map(s -> s.getTitulo()).collect(Collectors.toSet());
            dto.setSeriesTitulos(seriesTitulos);
        }

        return dto;
    }

    public List<ActoresDto> buscarActoresConFiltros(String nombre, String nacionalidad, Integer edadMin,
            Integer edadMax) {
        // Llama al repositorio personalizado para buscar actores con los filtros y luego los mapea a DTOs
        List<Actores> actores = actoresRepository.buscarActoresConFiltros(nombre, nacionalidad, edadMin, edadMax);
        return actores.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<ActoresDto> listarActores() {
        // Llama al repositorio para obtener todos los actores ordenados por nombre y los mapea a DTOs
        return actoresRepository.findAllByOrderByNombreAsc().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Actores guardarActores(Actores actores) {
        // Llama al repositorio para guardar un nuevo actor
        return actoresRepository.save(actores);
    }

    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    public Actores modificarActores(Actores actores) {
        // Busca un actor existente por su ID y, si existe, actualiza sus datos
        return actoresRepository.findById(actores.getIdActor()).map(actorExistente -> {
            actorExistente.setNombre(actores.getNombre());
            actorExistente.setApellido(actores.getApellido());
            actorExistente.setEdad(actores.getEdad());
            actorExistente.setNacionalidad(actores.getNacionalidad());
            return actoresRepository.save(actorExistente);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El actor a modificar no existe"));
        // Si no se encuentra el actor, lanza una excepción HTTP 404
    }

    public void borrarActores(String idActores) {
        // Llama al repositorio para eliminar un actor por su ID
        actoresRepository.deleteById(idActores);
    }

}