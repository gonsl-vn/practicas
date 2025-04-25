package viewnext.practica4.repositoriesCriteria;

import viewnext.practica4.entities.Peliculas;

import java.util.List;

/**
 * Interfaz Peliculas Repositorio Custom.
 */
public interface PeliculasRepositoryCustom {

    /**
     * Buscar peliculas utilizando filtros como título, año min, año max, productora, director.
     *
     * @param titulo
     *         the titulo
     * @param anioMin
     *         the anio min
     * @param anioMax
     *         the anio max
     * @param productora
     *         the productora
     * @param director
     *         the director
     * @return La lista de películas econtrado por estos filtros
     */
    List<Peliculas> buscarPeliculasConFiltros(String titulo, Integer anioMin, Integer anioMax, String productora,
            String director);

}
