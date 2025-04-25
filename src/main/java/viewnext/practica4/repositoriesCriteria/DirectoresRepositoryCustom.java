package viewnext.practica4.repositoriesCriteria;

import viewnext.practica4.entities.Directores;

import java.util.List;

/**
 * Interfaz Directores Repositorio Custom.
 */
public interface DirectoresRepositoryCustom {

    /**
     * Buscar directores utilizando filtros opcionales como nombre, nacionalidad, edad min, edad max.
     *
     * @param nombre
     *         the nombre
     * @param nacionalidad
     *         the nacionalidad
     * @param edadMin
     *         the edad min
     * @param edadMax
     *         the edad max
     * @return La lista de los directores encontrados por los filtros
     */
    List<Directores> buscarDirectoresConFiltros(String nombre, String nacionalidad, Integer edadMin, Integer edadMax);

}
