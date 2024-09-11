package com.thread.cdr.config;

import com.thread.cdr.BufferCompartido;
import com.thread.cdr.repository.CallHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AppConfig {

    @Value("${ruta.archivo}")
    private String rutaArchivo;

    @Bean
    public String rutaArchivo() {
        return rutaArchivo;
    }

    @Bean
    public int numeroHilos(){
        return 4;
    }

    @Bean
    public int hiloConsumidor(){
        return numeroHilos();
    }

    @Bean
    public int hiloProductor(){
        return numeroHilos();
    }

    @Autowired
    private CallHistoryRepository callHistoryRepository;

    @Bean
    public BufferCompartido bufferCompartido() {
        return new BufferCompartido(10);
    }




}