import React, { useEffect, useState } from 'react';
import { Client, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

function UploadForm(){
    const [file, setFile] = useState(null);
    const [producer, setProducer] = useState('');
    const [consumer, setConsumer] = useState('');
    const [socketClient, setSocketClient] = useState('');
    const [dbStatus, setDbStatus] = useState('');
    const [formDisabled, setFormDisabled] = useState(false);
    const [buttonDisabled, setButtonDisabled] = useState(false);
    const [buttonEnabled, setButtonEnabled] = useState(false);

    useEffect(() => {
        const socket = new SockJS('http://localhost:8084/ws');
        const client = new Client({
            brokerURL: 'ws://localhost:8084/ws',
            connectHeaders: {},
            debug: (str) => console.log(str),
            onConnect: () => {
                client.subscribe('/topic/db-status', (message) => {
                    setDbStatus(message.body);
                });
            },
            onDisconnect: () => {
                console.log('Disconnected from WebSocket');
            },
        });

        client.activate();

        setSocketClient(client);

        return () => {
            if (socketClient) {
                client.deactivate();
            }
        };
    },[]);

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleProducerChange = (event) => {
        setProducer(event.target.value);
    };

    const handleConsumerChange = (event) => {
        setConsumer(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('file', file);
        formData.append('producer', producer);
        formData.append('consumer', consumer);

        if (!file || !producer || !consumer) {
            alert('Por favor completa todos los campos y carga un archivo CSV.');
            return;
        }

        if (producer < 1 || producer > 20 || consumer < 1 || consumer > 20) {
            alert('El número de productores y consumidores debe estar entre 1 y 20.');
            return;
        }

        try {
            const response = await fetch('http://localhost:8084/api/v1/upload', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                setFormDisabled(true);
                setButtonEnabled(true);
                alert('Archivo subido exitosamente');
                socketClient.send('/app/db/write', {}, 'Datos cargados correctamente')
            } else {
                alert('Error al subir el archivo')
            }
        } catch (error) {
            console.log('Error al subir el archivo: ', error);
        }
    };

    const handleProcess = async () => {
        try {
            const response = await fetch('http://localhost:8084/api/v1/proccess', {
                method: 'GET',
            });
    
            if (response.ok) {
                alert('Procesamiento iniciado exitosamente');
                socketClient.send('/app/db/write', {}, 'Procesamiento en curso...');
            } else {
                alert('Error al iniciar el procesamiento');
            }
        } catch (error) {
            console.log('Error al iniciar el procesamiento: ', error);
        }
    };


    return (
        <div class='container text-center mb-4 border border-dark ' style={{ minWidth: '290px'}} >
            <div class='container text-center' style={{maxWidth:'90%'}}>
                <form onSubmit={handleSubmit} disabled={formDisabled}>
                    <div class='container justify-content-center row mb-3'>
                        <div class='col-4'>
                            <label for='handleFile' class='form-label'>Cargar archivo csv:</label>
                            <input class='form-control' id='handleFile' type='file' accept='.csv' onChange={handleFileChange}></input>
                        </div>
                    </div>
                    <div class='container justify-content-center row mb-3 '>
                        <div class='col-4'>
                            <label for='handleProducer' class='form-label'>Ingrese numero de procesos productores (1-20):</label>
                            <input class='form-control' id='handleProducer' type='number' onChange={handleProducerChange} min="1" max="20"></input>
                        </div>
                        <div class='col-4 '>
                            <label for='handleConsumer' class='form-label'>Ingrese numero de procesos consumidores (1-20):</label>
                            <input class='form-control' id='handleConsumer' type='number' onChange={handleConsumerChange} min="1" max="20"></input>
                        </div>
                    </div>
                    <div class='container justify-content-center row mb-3'>
                        <div class='col-3'>
                            <button type='submit'>Enviar</button>
                        </div>
                    </div>
                </form>
                {
                <div class='container justify-content-center row mb-3'>
                    <div class='col-3'>
                        <button onClick={handleProcess} disabled={!buttonEnabled}>Procesar</button>
                    </div>
                </div>  
                }
 
            </div>
        </div>
    )
}

export default UploadForm;