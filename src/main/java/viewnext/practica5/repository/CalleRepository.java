package viewnext.practica5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import viewnext.practica5.model.Calle;

import java.util.List;

public interface CalleRepository extends JpaRepository<Calle, Integer> {
    // Método personalizado para contar el número de Calles en un distrito específico
    long countByNombreDistrito(String nombreDistrito);

    // Consulta para obtener el nombre del distrito y el número total de calles en cada distrito
    @Query("SELECT c.nombreDistrito, COUNT(c) FROM Calle c GROUP BY c.nombreDistrito")
    List<Object[]> countViviendasPorDistrito();
}