package com.viewnext.springbatchf.jobs.job1.steps.processor;

import com.viewnext.springbatchf.models.Calle;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CalleItemProcessor implements ItemProcessor<Calle, Calle> {

    public static String filtroDistrito;

    public CalleItemProcessor(@Value("#{jobParameters['distrito']}") String filtroDistrito) {
        this.filtroDistrito = filtroDistrito;
    }

    @Override
    public Calle process(Calle calle) throws Exception {
        if (calle.getNombreCalle() == null || calle.getNomDistrito() == null) {
            throw new IllegalArgumentException("Campo obligatorio nulo");
        }

        if (!filtroDistrito.equalsIgnoreCase(calle.getNomDistrito().trim())) {
            return null;
        }

        calle.setNombreCalle(calle.getNombreCalle().toUpperCase());
        return calle;
    }

    public String getFiltroDistrito() {
        return filtroDistrito;
    }
}
