package viewnext.practica4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import viewnext.practica4.entities.Peliculas;
import viewnext.practica4.repositoriesCriteria.PeliculasRepositoryCustom;

import java.util.List;

/**
 * Interfaz Peliculas Repositorio.
 */
@Repository
public interface PeliculasRepository extends JpaRepository<Peliculas, Long>, PeliculasRepositoryCustom {

    /**
     * Buscar por título asc en una lista.
     *
     * @return La lista de Películas
     */
    List<Peliculas> findAllByOrderByTituloAsc();

}
