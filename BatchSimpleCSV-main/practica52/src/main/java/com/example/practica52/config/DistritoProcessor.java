package com.example.practica52.config;

import com.example.practica52.model.Calle;
import com.example.practica52.model.Distrito;
import com.example.practica52.repository.DistritoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class DistritoProcessor {

    private final Logger logger = LoggerFactory.getLogger(Slf4j.class);
    private final DistritoRepository distritoRepository;

    public DistritoProcessor(DistritoRepository distritoRepository) {
        this.distritoRepository = distritoRepository;
    }

    @Bean
    public ItemProcessor<Calle, Distrito> distritoNumeroCalleProcessor(){

        return calle-> {
            String nombreDistrito = calle.getNombreDistrito();
            Integer numeroCasasParOImpar = (calle.getUltimoNumTramo() - calle.getPrimerNumTramo())/2;

            if(distritoRepository.findByNombreDistrito(nombreDistrito)!= null){

                Distrito distrito = distritoRepository.findByNombreDistrito(nombreDistrito);
                Integer cuenta = distrito.getNumeroViviendas() + numeroCasasParOImpar;
                distrito.setNumeroViviendas(cuenta);
                return distrito;

            }else {
                Distrito distrito = new Distrito();
                distrito.setNombreDistrito(nombreDistrito);
                distrito.setNumeroViviendas(numeroCasasParOImpar);
                return distrito;
            }

        };
    }
}
