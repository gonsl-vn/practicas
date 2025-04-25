package viewnext.practica4.services;

import viewnext.practica4.entities.Productoras;
import viewnext.practica4.entitiesDTOs.ProductorasDto;

import java.util.List;

/**
 * The interface Productoras service.
 */
public interface ProductorasService {

    /**
     * Buscar productoras utilizando filtros como nombre, año min, año max.
     *
     * @param nombre
     *         the nombre
     * @param anioMin
     *         the anio min
     * @param anioMax
     *         the anio max
     * @return La lista de productoras encontrada por estos filtros
     */
    List<ProductorasDto> buscarProductorasConFiltros(String nombre, Integer anioMin, Integer anioMax);

    /**
     * Lista de Productoras
     *
     * @return La lista de productoras
     */
    List<ProductorasDto> listarProductoras();

    /**
     * Anadir uan productora nueva.
     *
     * @param productoras
     *         Objeto Productoras con los datos de la nueva productora
     * @return La productora añadida
     */
    Productoras anadirProductoras(Productoras productoras);

    /**
     * Modificar una productora ya existente.
     *
     * @param productoras
     *         Objeto Productoras con los datos actualizados
     * @return La productora modificada
     */
    Productoras modificarProductoras(Productoras productoras);

    /**
     * Borrar una productora por su ID.
     *
     * @param idProductoras
     *         El ID de la productora que se va a eliminar
     */
    void borrarProductoras(Long idProductoras);

}
