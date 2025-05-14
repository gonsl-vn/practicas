package viewnext.practica5.batch.config.writer;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.dto.UsuarioDTO;

import javax.sql.DataSource;

@Configuration
public class UsuarioCsvWriterConfig {

    private final DataSource dataSource; // DataSource para la conexión a la base de datos

    public UsuarioCsvWriterConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcBatchItemWriter<UsuarioDTO> usuarioWriter() {
        // Configura un escritor JDBC por lotes para insertar objetos UsuarioDTO en la base de datos
        return new JdbcBatchItemWriterBuilder<UsuarioDTO>().sql(
                        "INSERT INTO contenido_usuario (nombre, dni, direccion, ciudad, cod_postal, importe, num_pedido) " + "VALUES (:nombre, :dni, :direccion, :ciudad, :codPostal, :importe, :numPedido)") // Define la sentencia SQL de inserción, utilizando nombres de campos del DTO
                .dataSource(dataSource) // Asigna el DataSource para la conexión
                .beanMapped() // Indica que los parámetros de la sentencia SQL se mapearán a los campos del objeto UsuarioDTO por nombre
                .build(); // Construye el escritor
    }
}