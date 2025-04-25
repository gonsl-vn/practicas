package viewnext.practica4.services;

import viewnext.practica4.entities.Directores;
import viewnext.practica4.entitiesDTOs.DirectoresDto;

import java.util.List;

/**
 * The interface Directores service.
 */
public interface DirectoresService {

    /**
     * Busca directores utilizando filtros opcionales como nombre, nacionalidad y rango de edad.
     *
     * @param nombre
     *         the nombre
     * @param nacionalidad
     *         the nacionalidad
     * @param edadMin
     *         the edad min
     * @param edadMax
     *         the edad max
     * @return La lista de los directores encontrados por estos filtros
     */
    List<DirectoresDto> buscarDirectoresConFiltros(String nombre, String nacionalidad, Integer edadMin,
            Integer edadMax);

    /**
     * Listar directores.
     *
     * @return La lista de directores
     */
    List<DirectoresDto> listarDirectores();

    /**
     * Anadir un nuevo director.
     *
     * @param directores
     *         Objeto Directores con los datos del nuevo director
     * @return Director creado
     */
    Directores anadirDirectores(Directores directores);

    /**
     * Modificar un director ya existente.
     *
     * @param directores
     *         Director a modificar
     * @return El director modificado
     */
    Directores modificarDirectores(Directores directores);

    /**
     * Elimina un director por su ID.
     *
     * @param idDirectores
     *         ID del director a eliminar
     */
    void eliminarDirectores(String idDirectores);

}
