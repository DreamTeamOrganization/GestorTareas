import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './paginas/Home';
import Ingreso from './paginas/Ingreso'; // Este es el nuevo componente que crearás
import './App.css';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Ingreso />} />
      <Route path="/home" element={<Home />} />
    </Routes>
  );
}

export default App;
