package viewnext.practica4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import viewnext.practica4.entities.Series;
import viewnext.practica4.repositoriesCriteria.SeriesRepositoryCustom;

import java.util.List;

/**
 * Interfaz Series Repositorio.
 */
@Repository
public interface SeriesRepository extends JpaRepository<Series, Long>, SeriesRepositoryCustom {

    /**
     * Buscar por título asc en una lista.
     *
     * @return La lista de series
     */
    List<Series> findAllByOrderByTituloAsc();

}
