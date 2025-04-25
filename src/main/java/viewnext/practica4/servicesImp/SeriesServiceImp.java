package viewnext.practica4.servicesImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Actores;
import viewnext.practica4.entities.Directores;
import viewnext.practica4.entities.Productoras;
import viewnext.practica4.entities.Series;
import viewnext.practica4.entitiesDTOs.SeriesDto;
import viewnext.practica4.repositories.ActoresRepository;
import viewnext.practica4.repositories.DirectoresRepository;
import viewnext.practica4.repositories.ProductorasRepository;
import viewnext.practica4.repositories.SeriesRepository;
import viewnext.practica4.services.SeriesService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Series service imp.
 */
@Service // Anotación de Spring que marca esta clase como un servicio
public class SeriesServiceImp implements SeriesService {

    @Autowired
    private SeriesRepository seriesRepository; // Inyección de dependencia del repositorio de Series

    @Autowired
    private DirectoresRepository directoresRepository; // Inyección de dependencia del repositorio de Directores

    @Autowired
    private ProductorasRepository productorasRepository; // Inyección de dependencia del repositorio de Productoras

    @Autowired
    private ActoresRepository actoresRepository; // Inyección de dependencia del repositorio de Actores

    /**
     * Mapeo de Series a Series DTO.
     *
     * @param series
     *         El objeto Series
     * @return La Serie DTO
     */
    public SeriesDto mapToDto(Series series) { // Método para convertir una entidad Series a su DTO
        SeriesDto dto = new SeriesDto();
        dto.setIdSeries(series.getIdSeries());
        dto.setTitulo(series.getTitulo());
        dto.setAnio(series.getAnio());

        if (series.getDirectores() != null) {
            dto.setNombreDirector(series.getDirectores().getNombre());
        }

        if (series.getProductoras() != null) {
            dto.setNombreProductora(series.getProductoras().getNombre());
        }

        if (series.getActores() != null) {
            dto.setNombresActores(series.getActores().stream().map(Actores::getNombre).collect(Collectors.toSet()));
        }

        return dto;
    }

    public Series anadirSeries(SeriesDto seriesDto) {
        Series serie = new Series();
        serie.setTitulo(seriesDto.getTitulo());
        serie.setAnio(seriesDto.getAnio());

        // Busca y asigna el director a la serie
        Directores director = directoresRepository.findByNombre(seriesDto.getNombreDirector())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Director no encontrado"));
        serie.setDirectores(director);

        // Busca y asigna la productora a la serie
        Productoras productora = productorasRepository.findByNombre(seriesDto.getNombreProductora())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Productora no encontrada"));
        serie.setProductoras(productora);

        // Busca y asigna los actores a la serie
        if (seriesDto.getNombresActores() != null) {
            Set<Actores> actores = seriesDto.getNombresActores().stream()
                    .map(nombre -> actoresRepository.findByNombre(nombre).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor no encontrado: " + nombre)))
                    .collect(Collectors.toSet());
            serie.setActores(actores);
        }

        return seriesRepository.save(serie);
    }

    public List<SeriesDto> listarSeries() {
        // Llama al repositorio para obtener todas las series ordenadas por título y las mapea a DTOs
        return seriesRepository.findAllByOrderByTituloAsc().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Series modificarSeries(SeriesDto serieDto) {
        // Busca una serie existente por su ID y, si existe, actualiza sus datos y relaciones
        return seriesRepository.findById(serieDto.getIdSeries()).map(seriesExistente -> {
            seriesExistente.setTitulo(serieDto.getTitulo());
            seriesExistente.setAnio(serieDto.getAnio());

            // Actualiza el director si se proporciona un nombre
            if (serieDto.getNombreDirector() != null) {
                var director = directoresRepository.findByNombre(serieDto.getNombreDirector())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Director no encontrado"));
                seriesExistente.setDirectores(director);
            }

            // Actualiza la productora si se proporciona un nombre
            if (serieDto.getNombreProductora() != null) {
                var productora = productorasRepository.findByNombre(serieDto.getNombreProductora()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Productora no encontrada"));
                seriesExistente.setProductoras(productora);
            }

            // Actualiza los actores si se proporcionan nombres
            if (serieDto.getNombresActores() != null) {
                var actores = serieDto.getNombresActores().stream().map(nombre -> actoresRepository.findByNombre(nombre)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Actor no encontrado: " + nombre))).collect(Collectors.toSet());
                seriesExistente.setActores(actores);
            }

            return seriesRepository.save(seriesExistente);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La serie a modificar no existe"));
        // Si no se encuentra la serie, lanza una excepción HTTP 404
    }

    public List<SeriesDto> buscarSeriesConFiltros(String titulo, Integer anioMin, Integer anioMax, String director,
            String productora) {
        // Llama al repositorio personalizado para buscar series con los filtros y luego las mapea a DTOs
        return seriesRepository.buscarSeriesConFiltros(titulo, anioMin, anioMax, director, productora).stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }

    public void borrarSeries(Long idSeries) {
        // Llama al repositorio para eliminar una serie por su ID
        seriesRepository.deleteById(idSeries);
    }
}