package com.viewnext.practica3.services;

import com.viewnext.practica3.models.Usuario;
import com.viewnext.practica3.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServicesTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserServices userServices;

    private Usuario usuarioMock;
    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuarioActualizado;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario("Javier", "8821495Yi*", "Arias Rodriguez", 22);
        usuario1 = new Usuario("Juan", "12345678A", "Pérez", 30);
        usuario2 = new Usuario("María", "87654321B", "López", 25);
        usuarioActualizado = new Usuario("Jorge", "15954389S", "Mark", 50);
    }

    @Test
    void testListarUsuarios() {
        when(usersRepository.findAll()).thenReturn(Arrays.asList(usuarioMock, usuario1, usuario2));

        List<Usuario> userList = userServices.listarUsuarios();

        assertNotNull(userList);
        assertEquals(3, userList.size());
        assertEquals("8821495Yi*", userList.get(0).getDni());
        assertEquals("12345678A", userList.get(1).getDni());
        assertEquals("87654321B", userList.get(2).getDni());

        verify(usersRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorDni() {
        when(usersRepository.findByDni("8821495Yi*")).thenReturn(Optional.of(usuarioMock));

        Optional<Usuario> result = userServices.buscarPorDni("8821495Yi*");

        assertTrue(result.isPresent());
        assertEquals("Javier", result.get().getName());

        verify(usersRepository, times(1)).findByDni("8821495Yi*");
    }

    @Test
    void testBuscarPorNombre() {
        when(usersRepository.findByName("Javier")).thenReturn(usuarioMock);

        Usuario result = userServices.buscarPorNombre("Javier");

        assertNotNull(result);
        assertEquals("Javier", result.getName());

        verify(usersRepository, times(1)).findByName("Javier");
    }

    @Test
    void testBuscarPorApellido() {
        when(usersRepository.findBySurname("Arias Rodriguez")).thenReturn(usuarioMock);

        Usuario result = userServices.buscarPorApellido("Arias Rodriguez");

        assertNotNull(result);
        assertEquals("Arias Rodriguez", result.getSurname());

        verify(usersRepository, times(1)).findBySurname("Arias Rodriguez");
    }

    @Test
    void testBuscarPorEdad() {
        when(usersRepository.findByAge(22)).thenReturn(usuarioMock);

        Usuario result = userServices.buscarPorEdad(22);

        assertNotNull(result);
        assertEquals(22, result.getAge());

        verify(usersRepository, times(1)).findByAge(22);
    }

    @Test
    void testGuardarUsuario() {
        userServices.guardarUsuario(usuarioMock);

        verify(usersRepository, times(1)).save(usuarioMock);
    }

    @Test
    void testActualizarUsuario() {

        Usuario usuarioMock = new Usuario("Javier", "8821495Yi*", "Arias Rodriguez", 22);
        Usuario usuarioActualizado = new Usuario("Javi", "8821495Yi*", "Rodríguez", 23);

        when(usersRepository.findByDni(usuarioMock.getDni())).thenReturn(Optional.of(usuarioMock));
        when(usersRepository.save(any(Usuario.class))).thenReturn(null);

        userServices.actualizarUsuario(usuarioMock.getDni(), usuarioActualizado);

        assertEquals("Javi", usuarioMock.getName());
        assertEquals("Rodríguez", usuarioMock.getSurname());
        assertEquals(23, usuarioMock.getAge());

        verify(usersRepository, times(1)).findByDni(usuarioMock.getDni());
        verify(usersRepository, times(1)).save(usuarioMock);
    }

    @Test
    void testEliminarUsuario() {
        userServices.eliminarUsuario("8821495Yi*");

        verify(usersRepository, times(1)).deleteById("8821495Yi*");
    }

    @Test
    void testListarUsuariosNativo() {
        when(usersRepository.listarUsuariosNativo()).thenReturn(Arrays.asList(usuarioMock, usuario1, usuario2));

        List<Usuario> usuarios = userServices.listarUsuariosNativo();

        assertNotNull(usuarios);
        assertEquals(3, usuarios.size());

        verify(usersRepository, times(1)).listarUsuariosNativo();
    }

    @Test
    void testBuscarPorNombreNativo() {
        when(usersRepository.buscarPorNombreNativo("Javier")).thenReturn(Optional.of(usuarioMock));

        Optional<Usuario> usuarioEncontrado = userServices.buscarPorNombreNativo("Javier");

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("Javier", usuarioEncontrado.get().getName());

        verify(usersRepository, times(1)).buscarPorNombreNativo("Javier");
    }

    @Test
    void testBuscarPorDniNativo() {
        when(usersRepository.buscarPorDniNativo("8821495Yi*")).thenReturn(Optional.of(usuarioMock));

        Optional<Usuario> usuarioEncontrado = userServices.buscarPorDniNativo("8821495Yi*");

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("8821495Yi*", usuarioEncontrado.get().getDni());

        verify(usersRepository, times(1)).buscarPorDniNativo("8821495Yi*");
    }

    @Test
    void testBuscarPorEdadNativo() {
        when(usersRepository.buscarPorEdadNativo(22)).thenReturn(Optional.of(usuarioMock));

        Optional<Usuario> usuarioEncontrado = userServices.buscarPorEdadNativo(22);

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals(22, usuarioEncontrado.get().getAge());

        verify(usersRepository, times(1)).buscarPorEdadNativo(22);
    }

    @Test
    void testInsertarUsuarioNativo() {
        doNothing().when(usersRepository)
                .insertarUsuarioNativo(usuarioMock.getDni(), usuarioMock.getName(), usuarioMock.getSurname(),
                        usuarioMock.getAge());

        userServices.insertarUsuarioNativo(usuarioMock);

        verify(usersRepository, times(1)).insertarUsuarioNativo(usuarioMock.getDni(), usuarioMock.getName(),
                usuarioMock.getSurname(), usuarioMock.getAge());
    }

    @Test
    void testBorrarUsuarioNativo() {
        doNothing().when(usersRepository).borrarUsuarioNativo("8821495Yi*");

        userServices.borrarUsuarioNativo("8821495Yi*");

        verify(usersRepository, times(1)).borrarUsuarioNativo("8821495Yi*");
    }
}
