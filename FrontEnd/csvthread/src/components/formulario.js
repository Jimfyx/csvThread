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
        <div class=' '  >
            <form onSubmit={handleSubmit}>
                <div class='mb-3'>
                    <label for='handleFile' class='form-label'>Cargar archivo csv:</label>
                    <input class='form-control' id='handleFile' type='file' accept='.csv' onChange={handleFileChange}></input>
                </div>
            </form>
        </div>
    )
}

export default UploadForm;