package viewnext.practica3.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import viewnext.practica3.controllers.UsuarioController;
import viewnext.practica3.entities.Usuario;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UsuarioControllerTest {
    @Mock
    private UsuarioServiceImp usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("paco", "12345678A", "Pérez", 30);
    }

    @Test
    void testListarUsuarios() {
        // Esto es lo que debería devolver el servicio cuando se llame a listarUsuarios
        when(usuarioService.listarUsuarios()).thenReturn(Arrays.asList(usuario));

        ResponseEntity<List<Usuario>> response = usuarioController.listarUsuarios();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testListarUsuario() {
        // Esto es lo que debería devolver el servicio cuando se llame a listarUsuario con un DNI específico
        when(usuarioService.listarUsuario("12345678A")).thenReturn(usuario);

        // Llamada al controlador
        ResponseEntity<Usuario> response = usuarioController.listarUsuario("12345678A");

        // Verificaciones
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("paco", response.getBody().getNombre());
    }

    @Test
    void testAnadirUsuario() {
        // Esto es lo que debería devolver el servicio cuando se añada un nuevo usuario
        when(usuarioService.anadirUsuario(usuario)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.anadirUsuario(usuario);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testModificarUsuario() {
        // Esto es lo que debería devolver el servicio cuando se modifique el usuario
        when(usuarioService.modificarUsuario(usuario)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.modificarUsuario(usuario);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("paco", response.getBody().getNombre());
        assertEquals(30, response.getBody().getEdad());
    }

    @Test
    void testEliminarUsuario() {
        // Mock: lo que debe hacer el servicio cuando se elimine un usuario
        doNothing().when(usuarioService).eliminarUsuario("12345678A");

        ResponseEntity<Void> response = usuarioController.eliminarUsuario("12345678A");

        assertEquals(204, response.getStatusCodeValue());
    }
}

