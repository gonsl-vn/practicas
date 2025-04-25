package viewnext.practica4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import viewnext.practica4.entities.Actores;
import viewnext.practica4.repositoriesCriteria.ActoresRepositoryCustom;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz Actores Repositorio.
 */
@Repository
public interface ActoresRepository extends JpaRepository<Actores, String>, ActoresRepositoryCustom {

    /**
     * Buscar por nombre asc en una lista.
     *
     * @return La lista de Actores
     */
    List<Actores> findAllByOrderByNombreAsc();

    /**
     * Buscar por nombre opcional.
     *
     * @param nombre
     *         Nombre del actor
     * @return El actor encontrado por ese nombre
     */
    Optional<Actores> findByNombre(String nombre);

}
