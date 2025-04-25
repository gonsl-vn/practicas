package viewnext.practica4.servicesImp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Directores;
import viewnext.practica4.entitiesDTOs.DirectoresDto;
import viewnext.practica4.repositories.DirectoresRepository;
import viewnext.practica4.services.DirectoresService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Directores service imp.
 */
@Service // Anotación de Spring que marca esta clase como un servicio
public class DirectoresServiceImp implements DirectoresService {

    @PersistenceContext
    private EntityManager entityManager; // Gestiona la persistencia (aunque aquí no se usa directamente en los métodos principales)

    @Autowired
    private DirectoresRepository directoresRepository; // Inyección de dependencia del repositorio de Directores

    /**
     * Mapeo de directores a DTO.
     *
     * @param directores
     *         El objeto Directores
     * @return El Director DTO
     */
    public DirectoresDto mapToDto(Directores directores) { // Método para convertir una entidad Directores a su DTO
        DirectoresDto dto = new DirectoresDto();
        dto.setIdDirector(directores.getIdDirector());
        dto.setNombre(directores.getNombre());
        dto.setApellido(directores.getApellido());
        dto.setEdad(directores.getEdad());
        dto.setNacionalidad(directores.getNacionalidad());

        if (directores.getPeliculas() != null) {
            Set<String> peliculasTitulos = directores.getPeliculas().stream().map(p -> p.getTitulo())
                    .collect(Collectors.toSet());
            dto.setPeliculasTitulos(peliculasTitulos);
        }

        if (directores.getSeries() != null) {
            Set<String> seriesTitulos = directores.getSeries().stream().map(s -> s.getTitulo())
                    .collect(Collectors.toSet());
            dto.setSeriesTitulos(seriesTitulos);
        }

        return dto;
    }

    public List<DirectoresDto> buscarDirectoresConFiltros(String nombre, String nacionalidad, Integer edadMin,
            Integer edadMax) {
        // Llama al repositorio personalizado para buscar directores con los filtros y luego los mapea a DTOs
        List<Directores> directores = directoresRepository.buscarDirectoresConFiltros(nombre, nacionalidad, edadMin,
                edadMax);

        return directores.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<DirectoresDto> listarDirectores() {
        // Llama al repositorio para obtener todos los directores ordenados por nombre y los mapea a DTOs
        return directoresRepository.findAllByOrderByNombreAsc().stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Directores anadirDirectores(Directores directores) {
        // Llama al repositorio para guardar un nuevo director
        return directoresRepository.save(directores);
    }

    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    public Directores modificarDirectores(Directores directores) {
        // Busca un director existente por su ID y, si existe, actualiza sus datos
        return directoresRepository.findById(directores.getIdDirector()).map(directorExistente -> {
            directorExistente.setNombre(directores.getNombre());
            directorExistente.setApellido(directores.getApellido());
            directorExistente.setEdad(directores.getEdad());
            directorExistente.setNacionalidad(directores.getNacionalidad());
            return directoresRepository.save(directorExistente);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El director a modificar no existe"));
        // Si no se encuentra el director, lanza una excepción HTTP 404
    }

    public void eliminarDirectores(String idDirectores) {
        // Llama al repositorio para eliminar un director por su ID
        directoresRepository.deleteById(idDirectores);
    }
}