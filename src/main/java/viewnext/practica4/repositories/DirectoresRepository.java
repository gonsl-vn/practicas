package viewnext.practica4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import viewnext.practica4.entities.Directores;
import viewnext.practica4.repositoriesCriteria.DirectoresRepositoryCustom;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz Directores Repositorio.
 */
@Repository
public interface DirectoresRepository extends JpaRepository<Directores, String>, DirectoresRepositoryCustom {

    /**
     * Buscar por nombre asc en una lista.
     *
     * @return La lista de Directores
     */
    List<Directores> findAllByOrderByNombreAsc();

    /**
     * Buscar por nombre ocpional.
     *
     * @param nombre
     *         Nombre del director
     * @return El director encontrado por el nombre
     */
    Optional<Directores> findByNombre(String nombre);
}
