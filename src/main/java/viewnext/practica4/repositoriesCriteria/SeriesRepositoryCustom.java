package viewnext.practica4.repositoriesCriteria;

import viewnext.practica4.entities.Series;

import java.util.List;

/**
 * Interfaz Series Repositorio Custom.
 */
public interface SeriesRepositoryCustom {

    /**
     * Buscar series utilizando filtros como título, año min, año max, director, productora.
     *
     * @param titulo
     *         the titulo
     * @param anioMin
     *         the anio min
     * @param anioMax
     *         the anio max
     * @param director
     *         the director
     * @param productora
     *         the productora
     * @return La lista de las eries encontradas por estos filtros
     */
    List<Series> buscarSeriesConFiltros(String titulo, Integer anioMin, Integer anioMax, String director,
            String productora);

}
