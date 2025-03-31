package com.viewnext.springbatchf.components;

import com.viewnext.springbatchf.models.Calle;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SkipListenerCalle implements SkipListener<Calle, Calle> {

    private static final String LOG_FILE = "logs\\errores_registro.log";

    @Override
    public void onSkipInRead(Throwable throwable) {
        // Crear la carpeta 'logs' si no existe
        File logDirectory = new File("logs");
        if (!logDirectory.exists()) {
            logDirectory.mkdirs(); // Crea la carpeta
        }

        // Registra el error (por ejemplo, campos nulos, etc.)
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write("Causa: " + throwable.getMessage() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSkipInWrite(Calle item, Throwable t) {
        // Se registra el error de escritura
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println("[WRITE ERROR] Calle: " + item + " | Error: " + t.getMessage());
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el log de errores: " + e.getMessage());
        }
    }

    @Override
    public void onSkipInProcess(Calle item, Throwable t) {
        // Se registra el error de procesamiento
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println("[PROCESS ERROR] Calle: " + item + " | Error: " + t.getMessage());
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el log de errores: " + e.getMessage());
        }
    }
}
