package viewnext.practica3.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viewnext.practica3.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query(value = "SELECT u FROM Usuario u WHERE u.dni = :dni", nativeQuery = true)
    Usuario listarUsuario(@Param("dni") String dni);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Usuario u SET dni =:dni", nativeQuery = true)
    void modificarUsuario(@Param("dni") String dni, @Param("nombre") String nombre, @Param("apellido") String apellido,
            @Param("edad") Integer edad);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Usuario u WHERE u.dni = :dni", nativeQuery = true)
    void eliminarUsuario(@Param("dni") String dni);

    Usuario getByDni(String dni);
}
