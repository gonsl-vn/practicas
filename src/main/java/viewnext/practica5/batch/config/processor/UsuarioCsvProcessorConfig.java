package viewnext.practica5.batch.config.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.dto.UsuarioDTO;

@Configuration
public class UsuarioCsvProcessorConfig {

    @Bean
    public ItemProcessor<UsuarioDTO, UsuarioDTO> usuarioProcessor() {
        // Este procesador toma un UsuarioDTO como entrada y devuelve un UsuarioDTO como salida
        return usuario -> usuario; // Simplemente devuelve el mismo objeto UsuarioDTO sin modificaciones
    }
}