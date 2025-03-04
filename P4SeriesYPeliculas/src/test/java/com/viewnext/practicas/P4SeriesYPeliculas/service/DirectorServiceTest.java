package com.viewnext.practicas.P4SeriesYPeliculas.service;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.DirectorCriteriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class DirectorServiceTest {

    @Mock
    private DirectorCriteriaRepository directorCriteriaRepository;

    @InjectMocks
    private DirectorService directorService;
    private DirectorModel director1;
    private DirectorModel director2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        director1 = new DirectorModel("55555555F","alvaro","alvarez",
                20, "serbio",null, null);
        director2 = new DirectorModel("44444444G","Enrique", "Enriquez",
                30, "italiano", null, null);

        @Test
        void testBuscadorDirectores(){
            when(directorCriteriaRepository.buscarDirectoresPorCriteria(director1.getDni(),
                    director1.getName(),director1.getSurname(),director1.getAge(), director1.getNationality(),
                    director1.getPeliculas().stream().map(p->p.)))
        }
    }
}
