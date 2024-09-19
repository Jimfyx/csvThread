package com.thread.cdr.service;

import com.thread.cdr.BufferCarga;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LoadFileService implements Runnable {

    private final BufferCarga bufferCarga;
    private static final Lock fileLock = new ReentrantLock();

    public LoadFileService(BufferCarga bufferCarga) {
        this.bufferCarga = bufferCarga;
    }

    public void run() {}

    public void processFile(MultipartFile file) {
        try {
            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(file.getInputStream()));
            String linea;
            while ((linea = lector.readLine()) != null)
            {
                try {
                    bufferCarga.producir(linea);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("fin de la carga");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
