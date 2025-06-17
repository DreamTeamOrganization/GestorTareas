import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './paginas/Home';
import Ingreso from './paginas/Ingreso';
import './App.css';
import ProjectDetails from './paginas/ProjectDetails';
//devextreme
import 'devextreme/dist/css/dx.light.css';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Ingreso />} />
      <Route path="/home" element={<Home />} />
      <Route path="/projectDetails" element={<ProjectDetails />} />
    </Routes>
  );
}

export default App;
