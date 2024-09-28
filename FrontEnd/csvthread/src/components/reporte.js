import { useEffect, useState } from "react";

function Reporte(){
    const [accountNumber, setAccountNumber] = useState('');
    const [results, setResults] = useState('');

    useEffect(() => {
        if (accountNumber){
            const fetchResults = async () => {
                try {
                    const response = await fetch('http://localhost:8084/api/v1/find', {
                        method: 'GET',
                    });
        
                    if (response.ok) {
                        const data = response.text();
                        setResults(data);
                    } else {
                        setResults('No se encontraron coincidencias.')
                    }
                } catch (error) {
                    console.log('Error en la busqueda: ', error);
                    setResults('Error en la busqueda');
                }
            };
            fetchResults();
        }else {
            setResults('');
        }
    }, [accountNumber]);
    const handleAccount = (event) => {
        setAccountNumber(event.target.value);
    };

    return (
        <div className='container text-center mb-4 border border-dark' style={{ minWidth: '290px' }}>
            <div className='container text-center' style={{ maxWidth: '90%' }}>
                <div className='row mb-3'>
                    <div className='col-6'>
                        <label htmlFor='accountNumber' className='form-label'>Ingrese el número de cuenta:</label>
                        <input 
                            className='form-control' 
                            id='accountNumber' 
                            type='text' 
                            value={accountNumber}
                            onChange={handleAccount}
                            placeholder='Número de cuenta...'
                        />
                    </div>
                </div>

                <div className='row'>
                    <div className='col-12'>
                        <h5>Resultado:</h5>
                        <p>{setResults}</p>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default Reporte;