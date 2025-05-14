package viewnext.practica5.batch.config.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.model.DistritoResumen;
import viewnext.practica5.repository.CalleRepository;
import viewnext.practica5.repository.DistritoRepository;
import viewnext.practica5.repository.DistritoResumenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalleJobExecutionListener implements JobExecutionListener {

    private final CalleRepository calleRepository; // Repositorio para acceder a la información de las Calles
    private final DistritoResumenRepository resumenRepository; // Repositorio para guardar resúmenes de los distritos
    private final DistritoRepository distritoRepository; // Repositorio para guardar la información de los Distritos

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Se ejecuta antes de que inicie el Job
        log.info("Ejecutando job de calles con ID: {}", jobExecution.getId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Se ejecuta después de que finaliza el Job
        BatchStatus status = jobExecution.getStatus(); // Obtiene el estado de la ejecución
        String filtro = "ESTE"; // Se define el filtro para contar las calles

        if (status == BatchStatus.COMPLETED) {
            // Si el Job finalizó exitosamente
            long total = calleRepository.countByNombreDistrito(filtro); // Cuenta las calles que coinciden con el filtro

            DistritoResumen resumen = new DistritoResumen();
            resumen.setFiltroUsado(filtro);
            resumen.setNumeroRegistros(total);
            resumen.setEstadoBatch(status.toString());
            resumen.setTimestamp(LocalDateTime.now());
            resumenRepository.save(resumen); // Guarda el resumen en la base de datos

            log.info("Resumen de ejecución guardado: {} registros con distrito '{}'", total, filtro);

            List<Object[]> resultados = calleRepository.countViviendasPorDistrito(); // Obtiene el conteo de viviendas por distrito
            for (Object[] fila : resultados) {
                String nombreDistrito = (String) fila[0]; // Obtiene el nombre del distrito
                Long totalViviendas = (Long) fila[1]; // Obtiene el total de viviendas para ese distrito

                Distrito distrito = new Distrito();
                distrito.setNombreDistrito(nombreDistrito);
                distrito.setNumeroViviendas(totalViviendas.intValue());

                distritoRepository.save(distrito); // Guarda la información en la base de datos
                log.info("Insertado distrito '{}' con {} viviendas", nombreDistrito, totalViviendas);
            }
        } else {
            // Si el Job finalizó con algún error
            log.warn("Job de calles finalizó con estado: {}", status);
        }
    }
}