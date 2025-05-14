package viewnext.practica5.batch.config.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Distrito;

@Component
public class DistritoCsvWriter {

    public FlatFileItemWriter<Distrito> writer() {
        // Configura un escritor de archivos planos para objetos Distrito
        return new FlatFileItemWriterBuilder<Distrito>().name("distritoCsvWriter") // Nombre del escritor
                .resource(new FileSystemResource("distritos_exportados.csv")) // Ubicación del archivo de salida
                .delimited() // Indica que los campos estarán delimitados
                .delimiter(",") // El delimitador es la coma
                .names("id", "nombreDistrito", "numeroViviendas") // Nombres de las columnas en el archivo
                .headerCallback(
                        writer -> writer.write("id,nombreDistrito,numeroViviendas")) // Escribe la cabecera del archivo
                .build(); // Construye el escritor
    }
}