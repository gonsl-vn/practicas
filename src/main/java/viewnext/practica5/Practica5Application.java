package viewnext.practica5;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Practica5Application {

    public static void main(String[] args) {
        SpringApplication.run(Practica5Application.class, args);
    }

}