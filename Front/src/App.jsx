import { useState } from 'react'
import './App.css'
import { useEffect } from "react";

function App() {
  const [message, setMessage] = useState("");
  const [user, setUser] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const [isLoginView, setIsLoginView] = useState(true);

  useEffect(() => {
    fetch("http://localhost:8080/api/hello")
      .then((res) => res.text())
      .then((data) => setMessage(data));
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isLoginView) {
      if (!user || !password) {
        setError("Por favor ingrese usuario y contraseña");
        return;
      }
      // Lógica de registro
      setError("");
      alert(`Login attempt with user: ${user}`);
    } else {
      if (!user || !password || !confirmPassword) {
        setError("Por favor complete todos los campos");
        return;
      }
      if (password !== confirmPassword) {
        setError("Las contraseñas no coinciden");
        return;
      }
      // Lógica de incio de sesión
      setError("");
      alert(`Register attempt with user: ${user}`);
    }
  };

  return (
    <div className="app-container">
      <h1>{message}</h1>
      <div className="auth-form">
        <h2>{isLoginView ? 'Iniciar Sesión' : 'Registrarse'}</h2>
        {error && <p className="error">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Usuario</label>
            <input
              type="text"
              value={user}
              onChange={(e) => setUser(e.target.value)}
              placeholder="Ingrese su usuario"
            />
          </div>

          <div className="form-group">
            <label>Contraseña</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Ingrese su contraseña"
            />
          </div>
          {!isLoginView && (
            <div className="form-group">
              <label>Confirmar Contraseña</label>
              <input
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirme su contraseña"
              />
            </div>
          )}
          <button type="submit" className="submit-btn">
            {isLoginView ? 'Ingresar' : 'Registrarse'}
          </button>
        </form>
        <a
          href="#"
          className="toggle-link"
          onClick={(e) => {
            e.preventDefault();
            setIsLoginView(!isLoginView);
            setError("");
          }}
        >
          {isLoginView ? '¿No tienes cuenta? Regístrate' : '¿Ya tienes cuenta? Inicia sesión'}
        </a>
      </div>
    </div>
  );
}

export default App
