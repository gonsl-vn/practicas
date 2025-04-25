package viewnext.practica4.servicesImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica4.entities.Productoras;
import viewnext.practica4.entitiesDTOs.ProductorasDto;
import viewnext.practica4.repositories.ProductorasRepository;
import viewnext.practica4.services.ProductorasService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Productoras service imp.
 */
@Service // Anotación de Spring que marca esta clase como un servicio
public class ProductorasServiceImp implements ProductorasService {

    @Autowired
    private ProductorasRepository productorasRepository; // Inyección de dependencia del repositorio de Productoras

    /**
     * Mapeo de Productoras a DTO.
     *
     * @param productoras
     *         El objeto Productoras
     * @return La Productora DTO
     */
    public ProductorasDto mapToDto(Productoras productoras) { // Método para convertir una entidad Productoras a su DTO
        ProductorasDto dto = new ProductorasDto();
        dto.setIdProductora(productoras.getIdProductora());
        dto.setNombre(productoras.getNombre());
        dto.setAnioFundacion(productoras.getAnioFundacion());

        if (productoras.getPeliculas() != null) {
            Set<String> peliculasTitulos = productoras.getPeliculas().stream().map(p -> p.getTitulo())
                    .collect(Collectors.toSet());
            dto.setPeliculasTitulos(peliculasTitulos);
        }

        if (productoras.getSeries() != null) {
            Set<String> seriesTitulos = productoras.getSeries().stream().map(s -> s.getTitulo())
                    .collect(Collectors.toSet());
            dto.setSeriesTitulos(seriesTitulos);
        }

        return dto;

    }

    public List<ProductorasDto> listarProductoras() {
        // Llama al repositorio para obtener todas las productoras ordenadas por nombre y las mapea a DTOs
        return productorasRepository.findAllByOrderByNombreAsc().stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Productoras anadirProductoras(Productoras productoras) {
        // Llama al repositorio para guardar una nueva productora
        return productorasRepository.save(productoras);
    }

    public Productoras modificarProductoras(Productoras productoras) {
        // Busca una productora existente por su ID y, si existe, actualiza sus datos
        return productorasRepository.findById(productoras.getIdProductora()).map(productorasExistente -> {
            productorasExistente.setNombre(productoras.getNombre());
            productorasExistente.setAnioFundacion(productoras.getAnioFundacion());
            productorasExistente.setSeries(productoras.getSeries());
            productorasExistente.setPeliculas(productoras.getPeliculas());
            return productorasRepository.save(productorasExistente);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La productora a modificar no existe"));
        // Si no se encuentra la productora, lanza una excepción HTTP 404
    }

    public List<ProductorasDto> buscarProductorasConFiltros(String nombre, Integer anioMin, Integer anioMax) {
        // Llama al repositorio personalizado para buscar productoras con los filtros y luego las mapea a DTOs
        List<Productoras> productorasFiltradas = productorasRepository.buscarProductorasConFiltros(nombre, anioMin,
                anioMax);
        return productorasFiltradas.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public void borrarProductoras(Long idProductoras) {
        // Llama al repositorio para eliminar una productora por su ID
        productorasRepository.deleteById(idProductoras);
    }
}