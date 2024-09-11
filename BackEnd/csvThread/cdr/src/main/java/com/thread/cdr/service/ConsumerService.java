package com.thread.cdr.service;

import com.thread.cdr.BufferCompartido;
import com.thread.cdr.model.CallHistory;
import com.thread.cdr.repository.CallHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ConsumerService implements Runnable {
    private  final BufferCompartido buffer;

    private final CallHistoryRepository callHistoryRepository;

    private static final Lock lock = new ReentrantLock();

    @Autowired
    public ConsumerService(BufferCompartido buffer, CallHistoryRepository callHistoryRepository) {
        this.buffer = buffer;
        this.callHistoryRepository = callHistoryRepository;
    }

    @Override
    public void run() {
        try {
            while (true){
                String mensaje = buffer.consumir();

                if(mensaje.startsWith("End")){
                    System.out.println("Finalizado");
                    break;
                }
                lock.lock();
                try {

                    String[] parts = mensaje.split(",",9);
                    if(parts.length == 9){

                        String account = parts[0];
                        String origen = parts[1];
                        String destiny = parts[2];

                        LocalDateTime date_time;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        try {
                            date_time = LocalDateTime.parse(parts[3],formatter);
                        } catch (DateTimeParseException e) {
                            System.err.println("Error al obtener la fecha y hora: " + e.getMessage());
                            continue;
                        }

                        Integer duration = Integer.valueOf(parts[4]);
                        Float price = Float.valueOf(parts[5]);
                        String location = parts[6];
                        String idProductor = parts[7];

                        LocalTime start_time;
                        try {
                            start_time = LocalTime.parse(parts[8]);
                        } catch (DateTimeParseException e) {
                            System.err.println("Error al obtener la hora: " + e.getMessage());
                            continue;
                        }
                        LocalTime date_time_consumed = LocalTime.now();

                        CallHistory callHistory = new CallHistory(account, origen, destiny, date_time, duration, price, location,
                                idProductor, Thread.currentThread().getName(), date_time_consumed, LocalTime.now());
                        callHistoryRepository.save(callHistory);
                        System.out.println("Consumido por: " + Thread.currentThread().getName() + "," + account + ","
                                + origen + "," + destiny + "," + date_time);
                    }
                }finally{
                    lock.unlock();
                }
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
