package com.thread.cdr.service;

import com.thread.cdr.BufferCarga;
import com.thread.cdr.BufferCompartido;
import com.thread.cdr.repository.CallHistoryRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class ExecuteService {
    @Setter
    @Getter
    public int hiloProductor;
    @Setter
    @Getter
    public int hiloConsumidor;

    public ExecutorService executorServiceProductor(BufferCompartido bufferCompartido, BufferCarga bufferCarga) {
        ExecutorService executorService = Executors.newFixedThreadPool(hiloProductor);
        for (int i = 0; i < hiloProductor; i++) {
            String idProductor = "Productor " + i;
            ProducerService productor = new ProducerService(bufferCompartido, bufferCarga, idProductor);
            executorService.submit(productor);
            System.out.println("Productor " + i + ": " + productor);
        }
        return executorService;
    }
    public ExecutorService executorServiceConsumidor(BufferCompartido bufferCompartido, CallHistoryRepository callHistoryRepository) {
        ExecutorService executorService = Executors.newFixedThreadPool(hiloConsumidor);
        for (int i = 0; i < hiloConsumidor; i++) {
            String idConsumidor = "Consumidor " + i;
            ConsumerService consumidor = new ConsumerService(bufferCompartido, callHistoryRepository, idConsumidor);
            executorService.submit(consumidor);
            System.out.println("Consumidor " + i + ": " + consumidor);
        }
        return executorService;
    }
}
