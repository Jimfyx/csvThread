package com.thread.cdr.config;

import com.thread.cdr.BufferCompartido;
import com.thread.cdr.repository.CallHistoryRepository;
import com.thread.cdr.service.ConsumerService;
import com.thread.cdr.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Value("${ruta.archivo}")
    private String rutaArchivo;

    @Autowired
    private CallHistoryRepository callHistoryRepository;

    @Bean
    public BufferCompartido bufferCompartido() {
        return new BufferCompartido(10);
    }

    @Bean
    public ProducerService producerService(BufferCompartido bufferCompartido) {
        return new ProducerService(bufferCompartido, rutaArchivo);
    }

    @Bean
    public ExecutorService executorServiceProductor(BufferCompartido bufferCompartido) {
        int hiloProductor = 4;
        ExecutorService executorService = Executors.newFixedThreadPool(hiloProductor);
        for (int i = 0; i < hiloProductor; i++) {
            ProducerService productor = new ProducerService(bufferCompartido, rutaArchivo);
            executorService.submit(productor);
            System.out.println("Productor " + i + ": " + productor);
        }
        return executorService;
    }

    @Bean
    public ExecutorService executorServiceConsumidor(BufferCompartido bufferCompartido) {
        int hiloConsumidor = 4;
        ExecutorService executorService = Executors.newFixedThreadPool(hiloConsumidor);
        for (int i = 0; i < hiloConsumidor; i++) {
            ConsumerService consumidor = new ConsumerService(bufferCompartido, callHistoryRepository);
            executorService.submit(consumidor);
            System.out.println("Consumidor " + i + ": " + consumidor);
        }
        return executorService;
    }
}