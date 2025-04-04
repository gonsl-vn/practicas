package viewnext.practica3.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica3.entities.Usuario;
import viewnext.practica3.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImpTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImp usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("paco", "12345678A", "Pérez", 30);
    }

    @Test
    void testListarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    void testListarUsuario() {
        when(usuarioRepository.listarUsuario("12345678A")).thenReturn(usuario);

        Usuario resultado = usuarioService.listarUsuario("12345678A");

        assertNotNull(resultado);
        assertEquals("paco", resultado.getNombre());
    }

    @Test
    void testAnadirUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario nuevoUsuario = usuarioService.anadirUsuario(usuario);

        assertNotNull(nuevoUsuario);
        assertEquals("12345678A", nuevoUsuario.getDni());
    }

    @Test
    void testModificarUsuario() {
        Usuario usuarioModificado = new Usuario("paco", "12345678A", "Lopez", 28);

        when(usuarioRepository.getByDni("12345678A")).thenReturn(usuario);
        when(usuarioRepository.save(usuarioModificado)).thenReturn(usuarioModificado);

        Usuario resultado = usuarioService.modificarUsuario(usuarioModificado);

        assertNotNull(resultado);
        assertEquals("Lopez", resultado.getApellido());
        assertEquals(28, resultado.getEdad());

        verify(usuarioRepository).delete(usuario);
        verify(usuarioRepository).save(usuarioModificado);
    }

    @Test
    void testEliminarUsuario() {
        doNothing().when(usuarioRepository).eliminarUsuario("12345678A");

        assertDoesNotThrow(() -> usuarioService.eliminarUsuario("12345678A"));
    }
}
