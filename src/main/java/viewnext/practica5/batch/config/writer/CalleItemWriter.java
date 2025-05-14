package viewnext.practica5.batch.config.writer;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class CalleItemWriter {

    private final DataSource dataSource; // DataSource para la conexión a la base de datos

    public CalleItemWriter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcBatchItemWriter<Calle> writer() {
        // Configura un escritor JDBC por lotes para insertar objetos Calle en la base de datos
        String sql = "INSERT INTO calle (CODIGO_CALLE, TIPO_VIA, NOMBRE_CALLE, PRIMER_NUM_TRAMO, " + "ULTIMO_NUM_TRAMO, BARRIO, COD_DISTRITO, NOM_DISTRITO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return new JdbcBatchItemWriterBuilder<Calle>().dataSource(dataSource) // Asigna el DataSource para la conexión
                .sql(sql) // Define la sentencia SQL de inserción
                .itemPreparedStatementSetter(
                        new CallePreparedStatementSetter()) // Asigna el setter de parámetros para la sentencia SQL
                .build(); // Construye el escritor
    }

    private static class CallePreparedStatementSetter implements ItemPreparedStatementSetter<Calle> {
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