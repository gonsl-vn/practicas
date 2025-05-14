package viewnext.practica5.batch.config.writer;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class CalleCsvWriter {

    private final DataSource dataSource; // DataSource para la conexión a la base de datos

    public CalleCsvWriter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public FlatFileItemWriter<Calle> writer() {
        // Configura un escritor de archivos planos para objetos Calle
        return new FlatFileItemWriterBuilder<Calle>().name("calleCsvWriter")
                .resource(new FileSystemResource("calles_exportadas.csv")) // Ubicación del archivo de salida
                .delimited() // Indica que los campos estarán delimitados
                .delimiter(",") // El delimitador es la coma
                .names("codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio",
                        "codigoDistrito", "nombreDistrito") // Nombres de las columnas en el archivo
                .headerCallback(writer -> writer.write(
                        "codigoCalle,tipoVia,nombreCalle,primerNumTramo,ultimoNumTramo,barrio,codigoDistrito,nombreDistrito")) // Escribe la cabecera del archivo
                .build(); // Construye el escritor
    }

    @Bean
    public JdbcBatchItemWriter<Calle> calleWriterCompleto() {
        // Configura un escritor JDBC por lotes para insertar objetos Calle en la base de datos
        final String sqlInsert = "INSERT INTO calle (CODIGO_CALLE, TIPO_VIA, NOMBRE_CALLE, PRIMER_NUM_TRAMO, " + "ULTIMO_NUM_TRAMO, BARRIO, COD_DISTRITO, NOM_DISTRITO) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return new JdbcBatchItemWriterBuilder<Calle>().dataSource(dataSource) // Asigna el DataSource para la conexión
                .sql(sqlInsert) // Define la sentencia SQL de inserción
                .itemPreparedStatementSetter(
                        new CallePreparedStatementSetter()) // Asigna el setter de parámetros para la sentencia SQL
                .build(); // Construye el escritor
    }

    private static class CallePreparedStatementSetter
            implements org.springframework.batch.item.database.ItemPreparedStatementSetter<Calle> {
        @Override
        public void setValues(Calle calle, PreparedStatement ps) throws SQLException {
            // Establece los valores de los parámetros en la PreparedStatement para cada objeto Calle
            ps.setInt(1, calle.getCodigoCalle());
            ps.setString(2, calle.getTipoVia());
            ps.setString(3, calle.getNombreCalle());
            ps.setInt(4, calle.getPrimerNumTramo());
            ps.setInt(5, calle.getUltimoNumTramo());
            ps.setString(6, calle.getBarrio());
            ps.setInt(7, calle.getCodigoDistrito());
            ps.setString(8, calle.getNombreDistrito());
        }
    }
}