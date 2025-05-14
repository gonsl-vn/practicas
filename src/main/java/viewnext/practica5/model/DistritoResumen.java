package viewnext.practica5.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class DistritoResumen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filtroUsado;
    private Long numeroRegistros;
    private String estadoBatch;
    private LocalDateTime timestamp;
}
