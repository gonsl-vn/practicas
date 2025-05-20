package com.viewnext.Practica62Kafka.controller;

import com.viewnext.Practica62Kafka.model.Usuario;
import com.viewnext.Practica62Kafka.repository.UsuarioRepository;
import com.viewnext.Practica62Kafka.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/auth")
@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registroUsuario(@RequestBody Usuario usuario){
        boolean registrado = usuarioService.registrarUsuario(usuario);
        if(registrado){
            return ResponseEntity.ok("Usuario registrado con exito");
        }
        return ResponseEntity.noContent().build();

    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody Usuario usuario, HttpSession httpSession){
        boolean loguado = usuarioService.loginUsuario(usuario);
        if(loguado){
            return ResponseEntity.ok("Usuario logueado");
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.getNombre(),
                null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        httpSession.setAttribute("usuario", usuario.getNombre());
        return ResponseEntity.ok("usuario logueado correctamente");
    }
}
