package com.thread.cdr.controller;

import com.thread.cdr.BufferCarga;
import com.thread.cdr.BufferCompartido;
import com.thread.cdr.repository.CallHistoryRepository;
import com.thread.cdr.service.ExecuteService;
import com.thread.cdr.service.FileStorageService;
import org.apache.catalina.connector.Response;
import com.thread.cdr.service.LoadFileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1")
public class CdrController {


    private final LoadFileService loadFileService;
    private final FileStorageService fileStorageService;
    private final ExecuteService executeService;
    private final BufferCompartido bufferCompartido;
    private final BufferCarga bufferCarga;
    private final CallHistoryRepository callHistoryRepository;

    public CdrController(LoadFileService loadFileService,
                         FileStorageService fileStorageService, ExecuteService executeService,
                         BufferCompartido bufferCompartido, BufferCarga bufferCarga, CallHistoryRepository callHistoryRepository) {
        this.loadFileService = loadFileService;
        this.fileStorageService = fileStorageService;


        this.executeService = executeService;
        this.bufferCompartido = bufferCompartido;
        this.bufferCarga = bufferCarga;
        this.callHistoryRepository = callHistoryRepository;
    }

    @GetMapping("/test")
    public int response() {

        return Response.SC_OK;

    }

    @GetMapping("/proccess")
    public String startProccess() {
        executeService.executorServiceProductor(bufferCompartido, bufferCarga);
        executeService.executorServiceConsumidor(bufferCompartido, callHistoryRepository);
        return "File proccess started.";

    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("producer") int hiloProductor,
                             @RequestParam("consumer") int hiloConsumidor) throws IOException {
        if (file.isEmpty()){
            return "Error el archivo esta vacio.";
        }
        loadFileService.processFile(file);
        executeService.setHiloConsumidor(hiloConsumidor);
        executeService.setHiloProductor(hiloProductor);
        return "Archivo cargado.";
    }

    @PostMapping("/upload2")
    public String saveFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()){
            return "Error el archivo esta vacio.";
        }
        fileStorageService.saveFile(file);
        return "Archivo guardado.";
    }

    @GetMapping("/clear")
    public String clear() {
        bufferCarga.vaciar();
        return "Listo para carga de archivo.";
    }
}
