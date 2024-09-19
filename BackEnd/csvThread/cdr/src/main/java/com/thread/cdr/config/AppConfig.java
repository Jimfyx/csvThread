package com.thread.cdr.config;

import com.thread.cdr.BufferCarga;
import com.thread.cdr.BufferCompartido;
import com.thread.cdr.repository.CallHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

/*    @Bean
    public int hiloConsumidor(int hiloConsumidor){
        return hiloConsumidor;
    }

    @Bean
    public int hiloProductor(int hiloProductor){
        return hiloProductor;
    }*/

    @Bean
    public String idConsumidor(){
        return "consumidor";
    }

    @Autowired
    private CallHistoryRepository callHistoryRepository;

    @Bean
    public BufferCompartido bufferCompartido() {
        return new BufferCompartido(10);
    }

    @Bean
    public BufferCarga bufferCarga() {
        return new BufferCarga(10000);
    }
}