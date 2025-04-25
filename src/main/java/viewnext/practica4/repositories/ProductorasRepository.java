package viewnext.practica4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import viewnext.practica4.entities.Productoras;
import viewnext.practica4.repositoriesCriteria.ProductorasRepositoryCustom;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz Productoras Repositorio.
 */
@Repository
public interface ProductorasRepository extends JpaRepository<Productoras, Long>, ProductorasRepositoryCustom {

    /**
     * Buscar por nombre asc en una lista.
     *
     * @return La lista de Productoras
     */
    List<Productoras> findAllByOrderByNombreAsc();

    /**
     * Buscar por nombre opcional.
     *
     * @param nombre
     *         Nombre de la productora
     * @return La productora encontrada por el nombre
     */
    Optional<Productoras> findByNombre(String nombre);

}
