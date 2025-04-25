package viewnext.practica4.services;

import viewnext.practica4.entities.Series;
import viewnext.practica4.entitiesDTOs.SeriesDto;

import java.util.List;

/**
 * The interface Series service.
 */
public interface SeriesService {

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
     * @return La lista de series encontradas por estos filtros
     */
    List<SeriesDto> buscarSeriesConFiltros(String titulo, Integer anioMin, Integer anioMax, String director,
            String productora);

    /**
     * Lista de series.
     *
     * @return La lista de series
     */
    List<SeriesDto> listarSeries();

    /**
     * Anadir una nueva serie.
     *
     * @param seriesDto
     *         El objeto Serie que se va a añadir
     * @return La serie añadida
     */
    Series anadirSeries(SeriesDto seriesDto);

    /**
     * Modificar una serie ya existente.
     *
     * @param seriesDto
     *         Objeto series con los datos actualizados
     * @return La serie actualizada
     */
    Series modificarSeries(SeriesDto seriesDto);

    /**
     * Borrar una serie por su ID.
     *
     * @param idSeries
     *         El ID de la serie que va a ser borrada
     */
    void borrarSeries(Long idSeries);

}
