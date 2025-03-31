package com.example.practica52.config.processor;

import com.example.practica52.model.Calle;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class CalleProcessorTest {

    @Autowired
    private CalleProcessor calleProcessor;

    private ItemProcessor<Calle, Calle> processor;
    private JobParameters jobParameters;

    @Test
    void calleProcessorTest_shouldReturnCalleInDistritoEste() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("distritoAFiltrar", "ESTE")
                .addLong("hora :", LocalTime.now().toNanoOfDay())
                .toJobParameters();
        Calle calle1 = Calle.builder()
                .codigoCalle(28)
                .tipoVia("CL")
                .nombreCalle("ABARBANELL")
                .primerNumTramo(1)
                .ultimoNumTramo(7)
                .barrio("VALLE DE LOS GALANES")
                .codigoDistrito(2)
                .nombreDistrito("ESTE")
                .build();
        Calle calle2 = Calle.builder()
                .codigoCalle(28)
                .tipoVia("CL")
                .nombreCalle("ABARBANELL")
                .primerNumTramo(2)
                .ultimoNumTramo(6)
                .barrio("VALLE DE LOS GALANES")
                .codigoDistrito(2)
                .nombreDistrito("ESTE")
                .build();
        Calle calle3 = Calle.builder()
                .codigoCalle(76692)
                .tipoVia("CL")
                .nombreCalle("ABOGADO VICTORIANO FRIAS")
                .primerNumTramo(1)
                .ultimoNumTramo(25)
                .barrio("EL PALO")
                .codigoDistrito(2)
                .nombreDistrito("ESTE")
                .build();
        Calle calle4 = Calle.builder()
                .codigoCalle(77772)
                .tipoVia("CL")
                .nombreCalle("VICTORIANO FRIAS")
                .primerNumTramo(1)
                .ultimoNumTramo(25)
                .barrio("EL PALO")
                .codigoDistrito(3)
                .nombreDistrito("MADRILES")
                .build();

        StepExecution stepExecution = MetaDataInstanceFactory
                .createStepExecution(jobParameters);

        StepScopeTestUtils.doInStepScope(stepExecution, ()->{

            Calle calleAnswer1 = calleProcessor.process(calle1);
            Calle calleAnswer2 = calleProcessor.process(calle2);
            Calle calleAnswer3 = calleProcessor.process(calle3);
            Calle calleAnswer4 = calleProcessor.process(calle4);

            assertEquals(calle1, calleAnswer1);
            assertEquals(calle2, calleAnswer2);
            assertEquals(calle3, calleAnswer3);

            assertNull(calleAnswer4);

            return  null;
        });

    }
}
