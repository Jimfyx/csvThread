import './App.css';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Navbar from './components/navbar';
import Inicio from './components/inicio';
import Operativo from './components/operativo';
import Reporte from './components/reporte';


function App() {
  return (
    <Router>
      <div>
        <Navbar />
      </div>
      <Routes>
        <Route path='/' element={<Inicio />} />
        <Route path='/operativo' element={<Operativo />} />
        <Route path='/reporte' element={<Reporte />} />
      </Routes>
    </Router>

  );
}

export default App;
