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
        setError("Por favor ingresa usuario y contraseña");
        return;
      }

      const handleSubmit = async (e) => {
        e.preventDefault();
      
        if (isLoginView) {
          if (!user || !password) {
            setError("Por favor ingresa usuario y contraseña");
            return;
          }
      
          // PETICIÓN POST para login
          try {
            const response = await fetch("http://localhost:8080/api/usuarios/login", {
              method: "POST",
              headers: {
                "Content-Type": "application/json"
              },
              body: JSON.stringify({ username: user, password: password })
            });
      
            if (!response.ok) throw new Error("Error en la autenticación");
            const data = await response.json();
            console.log("Login exitoso:", data);
            setError("");
            alert(`Login exitoso para: ${user}`);
          } catch (err) {
            console.error(err);
            setError("Login fallido. Verifica tus credenciales.");
          }
      
        } else {
          if (!user || !password || !confirmPassword) {
            setError("Por favor completa todos los campos");
            return;
          }
          if (password !== confirmPassword) {
            setError("Las contraseñas no coinciden");
            return;
          }
      
          // PETICIÓN POST para registro

          const credentials = btoa("superduperuser:user");
          try {
            const response = await fetch("http://localhost:8080/api/usuarios/add", {
              method: "POST",
              headers: {
                "Authorization": `Basic ${credentials}`,
                "Content-Type": "application/json"
              },
              body: JSON.stringify({ username: user, password: password })
            });
      
            if (!response.ok) throw new Error("Error en el registro");
            const data = await response.json();
            console.log("Registro exitoso:", data);
            setError("");
            alert(`Registro exitoso para: ${user}`);
          } catch (err) {
            console.error(err);
            setError("Registro fallido. Intenta de nuevo.");
          }
        }
      };
      
      // Lógica de registro
      setError("");
      alert(`Login attempt with user: ${user}`);
    } else {
      if (!user || !password || !confirmPassword) {
        setError("Por favor completa todos los campos");
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
        <h2>{isLoginView ? 'Inicia Sesión' : 'Registrate'}</h2>
        {error && <p className="error">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Usuario</label>
            <input
              type="text"
              value={user}
              onChange={(e) => setUser(e.target.value)}
              placeholder="Ingresa usuario"
            />
          </div>

          <div className="form-group">
            <label>Contraseña</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Ingresa contraseña"
            />
          </div>
          {!isLoginView && (
            <div className="form-group">
              <label>Confirma tu contraseña</label>
              <input
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirma tu contraseña"
              />
            </div>
          )}
          <button type="submit" className="submit-btn" onClick={(e) => {
            console.log(user);
          }}>
            {isLoginView ? 'Ingresa' : 'Registrate'}
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
