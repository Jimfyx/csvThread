package com.thread.cdr.service;

import com.thread.cdr.BufferCarga;
import com.thread.cdr.BufferCompartido;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProducerService implements Runnable {

    private final BufferCompartido buffer;
    private final BufferCarga bufferCarga;
    private final String idProductor;
    private static final Lock lock = new ReentrantLock();

    public ProducerService(BufferCompartido buffer, BufferCarga bufferCarga, String idProductor) {
        this.buffer = buffer;
        this.bufferCarga = bufferCarga;
        this.idProductor = idProductor;
    }

    @Override
    public void run() {
        try{

            BufferCarga lector = bufferCarga;
            String linea;
            while ((linea = lector.consumir()) != null) {
                try {
                    lock.lock();
                    LocalTime started_time = LocalTime.now();

                    String mensaje = linea + "," + idProductor + "," + started_time;
                    buffer.producir(mensaje);
                    System.out.println("Producido: " + mensaje);
                    //Thread.sleep((int) (Math.random() * 6000));

                } finally {
                    lock.unlock();
                }

            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        }
    }

