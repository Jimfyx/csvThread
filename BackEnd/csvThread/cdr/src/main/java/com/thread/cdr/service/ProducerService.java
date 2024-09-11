package com.thread.cdr.service;

import com.thread.cdr.BufferCompartido;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProducerService implements Runnable {

    private final BufferCompartido buffer;
    private final String rutaArchivo;
    private static final Lock fileLock = new ReentrantLock();

    public ProducerService(BufferCompartido buffer, String rutaArchivo) {
        this.buffer = buffer;
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void run() {
        try{

            BufferedReader lector = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(rutaArchivo)));
            String linea;
            while ((linea = lector.readLine()) != null) {
                try {
                    fileLock.lock();
                    LocalTime started_time = LocalTime.now();

                    String mensaje = linea + "," + Thread.currentThread().getName() + "," + started_time;
                    buffer.producir(mensaje);
                    System.out.println("Producido: " + mensaje);
                    //Thread.sleep((int) (Math.random() * 1000));

                } finally {
                    fileLock.unlock();
                }

            }
        }catch (IOException | InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            try{
                buffer.producir("End, " + Thread.currentThread().getName());
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
