package viewnext.practica4.services;

import viewnext.practica4.entities.Peliculas;
import viewnext.practica4.entitiesDTOs.PeliculasDto;

import java.util.List;

/**
 * The interface Peliculas service.
 */
public interface PeliculasService {

    /**
     * Buscar peliculas con filtros list.
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
     * @return the list
     */
    List<PeliculasDto> buscarPeliculasConFiltros(String titulo, Integer anioMin, Integer anioMax, String productora,
            String director);

    /**
     * Listar peliculas list.
     *
     * @return the list
     */
    List<PeliculasDto> listarPeliculas();

    /**
     * Anadir pelicula peliculas.
     *
     * @param peliculas
     *         the peliculas
     * @return the peliculas
     */
    Peliculas anadirPelicula(Peliculas peliculas);

    /**
     * Modificar peliculas peliculas.
     *
     * @param peliculas
     *         the peliculas
     * @return the peliculas
     */
    Peliculas modificarPeliculas(Peliculas peliculas);

    /**
     * Borrar peliculas.
     *
     * @param idPeliculas
     *         the id peliculas
     */
    void borrarPeliculas(Long idPeliculas);

}
