package com.viewnext.practicas.practica5Batch.step.chunk;

import com.viewnext.practicas.practica5Batch.model.Calle;
import com.viewnext.practicas.practica5Batch.repository.CalleRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalleItemWriter implements ItemWriter<Calle> {

    private final CalleRepository calleRepository;

    public CalleItemWriter(CalleRepository calleRepository) {
        this.calleRepository = calleRepository;
    }
    @Override
    public void write(Chunk<? extends Calle> calles) throws Exception {
        calleRepository.saveAll(calles);
        System.out.println("Se han guardado las calles");
    }
}
