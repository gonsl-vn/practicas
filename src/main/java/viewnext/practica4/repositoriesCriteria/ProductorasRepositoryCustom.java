package viewnext.practica4.repositoriesCriteria;

import viewnext.practica4.entities.Productoras;

import java.util.List;

/**
 * Interfaz Productoras Repositorio Custom.
 */
public interface ProductorasRepositoryCustom {

    /**
     * Buscar productoras utilizando filtros como nombre, año min, año max.
     *
     * @param nombre
     *         the nombre
     * @param anioMin
     *         the anio min
     * @param anioMax
     *         the anio max
     * @return La lista de las productoras encontradas por estos filtros
     */
    List<Productoras> buscarProductorasConFiltros(String nombre, Integer anioMin, Integer anioMax);

}
