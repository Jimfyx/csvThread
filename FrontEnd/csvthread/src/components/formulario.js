import React, { useState } from 'react';

function UploadForm(){
    const [file, setFile] = useState(null);
    const [producer, setProducer] = useState('');
    const [consumer, setConsumer] = useState('');

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

        try {
            const response = await fetch('http://localhost:3306/upload', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                alert('Archivo subido exitosamente');
            } else {
                alert('Error al subir el archivo')
            }
        } catch (error) {
            console.log('Error al subir el archivo: ', error);
            alert('Error en la carga del archivo al servidor');
        }
    };

    return (
        <div class='container text-center mb-4 border border-dark ' style={{ minWidth: '290px'}} >
            <div class='container text-center' style={{maxWidth:'90%'}}>
                <form onSubmit={handleSubmit}>
                    <div class='container justify-content-center row mb-3'>
                        <div class='col-4'>
                            <label for='handleFile' class='form-label'>Cargar archivo csv:</label>
                            <input class='form-control' id='handleFile' type='file' accept='.csv' onChange={handleFileChange}></input>
                        </div>
                    </div>
                    <div class='container justify-content-center row mb-3 '>
                        <div class='col-4'>
                            <label for='handleProducer' class='form-label'>Ingrese numero de procesos productores:</label>
                            <input class='form-control' id='handleProducer' type='number' onChange={handleProducerChange}></input>
                        </div>
                        <div class='col-4 '>
                            <label for='handleConsumer' class='form-label'>Ingrese numero de procesos consumidores:</label>
                            <input class='form-control' id='handleConsumer' type='number' onChange={handleConsumerChange}></input>
                        </div>
                    </div>
                    <div class='container justify-content-center row mb-3'>
                        <div class='col-3'>
                            <button type='submit'>Enviar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default UploadForm;