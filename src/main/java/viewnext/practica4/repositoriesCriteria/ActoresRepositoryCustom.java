package viewnext.practica4.repositoriesCriteria;

import viewnext.practica4.entities.Actores;

import java.util.List;

/**
 * Interfaz Actores Repositorio Custom.
 */
public interface ActoresRepositoryCustom {

    /**
     * Buscar actores utilizando filtros opcionales como nombre, nacionalidad, edad min, edad max.
     *
     * @param nombre
     *         the nombre
     * @param nacionalidad
     *         the nacionalidad
     * @param edadMin
     *         the edad min
     * @param edadMax
     *         the edad max
     * @return Lista de los actores encontrados por estos filtros
     */
    List<Actores> buscarActoresConFiltros(String nombre, String nacionalidad, Integer edadMin, Integer edadMax);

}
