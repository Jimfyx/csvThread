package com.thread.cdr;

import com.thread.cdr.repository.CallHistoryRepository;
import com.thread.cdr.service.ConsumerService;
import com.thread.cdr.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootApplication
public class CdrApplication {

    @Autowired
    private int hiloProductor;

    @Autowired
    private int hiloConsumidor;

    @Autowired
    private BufferCompartido bufferCompartido;

    public static void main(String[] args) {
        SpringApplication.run(CdrApplication.class, args);
    }

    @Bean
    public ExecutorService executorServiceProductor(BufferCompartido bufferCompartido, String rutaArchivo) {
        ExecutorService executorService = Executors.newFixedThreadPool(hiloProductor);
        for (int i = 0; i < hiloProductor; i++) {
            Thread.currentThread().setName("Productor " + i);
            ProducerService productor = new ProducerService(bufferCompartido, rutaArchivo);
            executorService.submit(productor);
            System.out.println("Productor " + i + ": " + productor);
        }
        return executorService;
    }

    @Bean
    public ExecutorService executorServiceConsumidor(BufferCompartido bufferCompartido, CallHistoryRepository callHistoryRepository) {
        ExecutorService executorService = Executors.newFixedThreadPool(hiloConsumidor);
        for (int i = 0; i < hiloConsumidor; i++) {
            Thread.currentThread().setName("Consumidor " + i);
            ConsumerService consumidor = new ConsumerService(bufferCompartido, callHistoryRepository);
            executorService.submit(consumidor);
            System.out.println("Consumidor " + i + ": " + consumidor);
        }
        return executorService;
    }
}
