import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Ingreso() {
    const navigate = useNavigate(); //navigation dentro del router
    const [message, setMessage] = useState("");
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");
    const [isLoginView, setIsLoginView] = useState(true);
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        const credentials = btoa("superduperuser:user");
        
        if (isLoginView) {
            if (!user || !password) {
                setError("Por favor ingresa usuario y contraseña");
                return;
            }
            
            try {
                const response = await fetch("http://localhost:8080/api/users/login", {
                    method: "POST",
                    headers: {
                        "Authorization": `Basic ${credentials}`,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ username: user, password: password })
                });
                
                if (!response.ok) throw new Error("Error en la autenticación");
                setError("");
                alert(`Inicio de sesión exitoso para: ${user}`);
                navigate("/home");
            } catch (err) {
                console.error(err);
                setError("Inicio de sesión fallido. Verifica tus credenciales.");
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
            
            try {
                const response = await fetch("http://localhost:8080/api/users/add", {
                    method: "POST",
                    headers: {
                        "Authorization": `Basic ${credentials}`,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ username: user, password: password })
                });
                
                if (!response.ok) throw new Error("Error en el registro");
                setError("");
                alert(`Registro exitoso para: ${user}`);
            } catch (err) {
                console.error(err);
                setError("Registro fallido. Intenta de nuevo.");
            }
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

          <button type="submit" className="submit-btn">
            {isLoginView ? 'Ingresa' : 'Regístrate'}
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

export default Ingreso;
