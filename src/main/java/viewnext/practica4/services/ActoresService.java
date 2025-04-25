package viewnext.practica4.services;

import viewnext.practica4.entities.Actores;
import viewnext.practica4.entitiesDTOs.ActoresDto;

import java.util.List;

/**
 * The interface Actores service.
 */
public interface ActoresService {

    /**
     * Buscar actores utilizando filtros como nombre, nacionalidad, edad min, edad max.
     *
     * @param nombre
     *         the nombre
     * @param nacionalidad
     *         the nacionalidad
     * @param edadMin
     *         the edad min
     * @param edadMax
     *         the edad max
     * @return La lista de actores encontrados por estos filtros
     */
    List<ActoresDto> buscarActoresConFiltros(String nombre, String nacionalidad, Integer edadMin, Integer edadMax);

    /**
     * Listar actores.
     *
     * @return La lista de actores
     */
    List<ActoresDto> listarActores();

    /**
     * Guarda un nuevo actor.
     *
     * @param actores
     *         Actor a guardar
     * @return Actor guardado
     */
    Actores guardarActores(Actores actores);

    /**
     * Modifica un actor existente.
     *
     * @param actores
     *         Actor actor
     * @return Actor modificado
     */
    Actores modificarActores(Actores actores);

    /**
     * Elimina un actor por su ID.
     *
     * @param idActores
     *         ID del actor a eliminar
     */
    void borrarActores(String idActores);

}
