package com.thread.cdr;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class BufferCarga {
    private final BlockingQueue<String> buffer;

    public BufferCarga(int size) {
        this.buffer = new ArrayBlockingQueue<>(size);
    }

    public void producir(String item) throws InterruptedException {
        buffer.put(item);
    }

    public String consumir() throws InterruptedException {
        String item = buffer.take();
        return item;
    }

    public void vaciar(){
        buffer.clear();
    }
}
