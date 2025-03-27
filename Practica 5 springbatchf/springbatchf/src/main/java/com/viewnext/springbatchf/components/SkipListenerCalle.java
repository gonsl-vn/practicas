package com.viewnext.springbatchf.components;

import com.viewnext.springbatchf.model.Calle;
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
        // Crear la carpeta si no existe
        File logDirectory = new File("logs");
        if (!logDirectory.exists()) {
            logDirectory.mkdirs();  // Crea la carpeta si no existe
        }

        // Aquí se registra el error de procesamiento (por ejemplo, campos nulos)
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write("Causa: " + throwable.getMessage() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSkipInWrite(Calle item, Throwable t) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println("[WRITE ERROR] Calle: " + item + " | Error: " + t.getMessage());
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el log de errores: " + e.getMessage());
        }
    }

    @Override
    public void onSkipInProcess(Calle item, Throwable t) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println("[PROCESS ERROR] Calle: " + item + " | Error: " + t.getMessage());
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el log de errores: " + e.getMessage());
        }
    }

}
