package viewnext.practica4.servicesImp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Peliculas;
import viewnext.practica4.entitiesDTOs.PeliculasDto;
import viewnext.practica4.repositories.PeliculasRepository;
import viewnext.practica4.services.PeliculasService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Peliculas service imp.
 */
@Service // Anotación de Spring que marca esta clase como un servicio
public class PeliculasServiceImp implements PeliculasService {

    @PersistenceContext
    private EntityManager entityManager; // Gestiona la persistencia (aunque aquí no se usa directamente en los métodos principales)

    @Autowired
    private PeliculasRepository peliculasRepository; // Inyección de dependencia del repositorio de Peliculas

    /**
     * Mapeo de Peliculas a DTO.
     *
     * @param peliculas
     *         El objeto de Peliculas
     * @return La Pelicula DTO
     */
    public PeliculasDto mapToDto(Peliculas peliculas) { // Método para convertir una entidad Peliculas a su DTO
        PeliculasDto dto = new PeliculasDto();
        dto.setIdPelicula(peliculas.getIdPelicula());
        dto.setTitulo(peliculas.getTitulo());
        dto.setAnio(peliculas.getAnio());
        dto.setProductoras(peliculas.getProductoras());
        dto.setDirectores(peliculas.getDirectores());
        dto.setActores(peliculas.getActores());

        return dto;
    }

    public List<PeliculasDto> listarPeliculas() {
        // Llama al repositorio para obtener todas las películas ordenadas por título y las mapea a DTOs
        return peliculasRepository.findAllByOrderByTituloAsc().stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    public Peliculas anadirPelicula(Peliculas peliculas) {
        // Llama al repositorio para guardar una nueva película
        return peliculasRepository.save(peliculas);
    }

    @Transactional // Anotación que indica que este método debe ejecutarse dentro de una transacción
    public Peliculas modificarPeliculas(Peliculas peliculas) {
        // Busca una película existente por su ID y, si existe, actualiza sus datos
        return peliculasRepository.findById(peliculas.getIdPelicula()).map(peliculaExistente -> {
            peliculaExistente.setTitulo(peliculas.getTitulo());
            peliculaExistente.setActores(peliculas.getActores());
            peliculaExistente.setAnio(peliculas.getAnio());
            return peliculasRepository.save(peliculaExistente);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La película a modificar no existe"));
        // Si no se encuentra la película, lanza una excepción HTTP 404
    }

    public void borrarPeliculas(Long idPelicula) {
        // Llama al repositorio para eliminar una película por su ID
        peliculasRepository.deleteById(idPelicula);
    }

    public List<PeliculasDto> buscarPeliculasConFiltros(String titulo, Integer anioMin, Integer anioMax,
            String productora, String director) {
        // Llama al repositorio personalizado para buscar películas con los filtros y luego las mapea a DTOs
        List<Peliculas> peliculasFiltradas = peliculasRepository.buscarPeliculasConFiltros(titulo, anioMin, anioMax,
                productora, director);

        return peliculasFiltradas.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}