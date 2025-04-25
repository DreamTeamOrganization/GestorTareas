import React, { useEffect, useState, useRef } from 'react';
import glasses from '../assets/glasses.svg';
import 'bootstrap/dist/css/bootstrap.min.css';

const Home = () => {
  const [projects, setProjects] = useState([]);
  const [userId, setUserId] = useState([]);
  const [username, setUsername] = useState(null);
  const [showUserModal, setShowUserModal] = useState(false);
  const [modalTop, setModalTop] = useState(0);
  const userCircleRef = useRef(null);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [newProjectName, setNewProjectName] = useState('');
  const [newDescription, setNewDescription] = useState('');
  const credentials = btoa("superduperuser:user");
  
  const fetchProjects = async () => {
    const response = await fetch(`http://localhost:8080/api/projects/user/${storeUserId}`, {
      method: "GET",
      headers: {
        "Authorization": `Basic ${credentials}`,
        "Content-Type": "application/json"
      },
    });
    const result = await response.json();
    setProjects(result);
  };
  
  useEffect(() => {
    const storeUserId = localStorage.getItem("userId");
    const storeUsername = localStorage.getItem("username");
    
    if (storeUserId) {
      setUserId(storeUserId);
    }
    
    if (storeUsername) {
      setUsername(storeUsername);
    }
    
    fetchProjects();
    
  }, [userId]);
  
  const handleProjectClick = (project) => {
    alert(`Project selected: ${project.name}`);
  };
  
  const handleCreateProject = async () => {
    const credentials = btoa("superduperuser:user");
    const response = await fetch(`http://localhost:8080/api/projects/add`, {
      method: "GET",
      headers: {
        "Authorization": `Basic ${credentials}`,
        "Content-Type": "application/json"
      },
      //body: JSON.stringify({ name: newProjectName, description: newDescription, id_user: userId })
    });
    if (!response.ok) throw new Error("Error al crear el proyecto");

    alert('Proyecto creado exitosamente');
    setShowCreateModal(false);
    setNewProjectName("");
    setNewDescription("");
    fetchProjects(); //refrescar la lista

  };
  
  const handleOpenCreateModal = () => setShowCreateModal(true);
  const handleCloseCreateModal = () => setShowCreateModal(false);

  const handleUserClick = () => {
    if (userCircleRef.current) {
      const rect = userCircleRef.current.getBoundingClientRect();
      setModalTop(rect.bottom + window.scrollY); // debajo del circulo
    }
    setShowUserModal(true);
  };

  const handleCloseModal = () => {
    setShowUserModal(false);
  };

  return (
    <div style={{ height: '100vh', width: '100%', display: 'flex', flexDirection: 'column' }}>
      {/* Navbar */}
      <nav className="navbar navbar-light bg-white border-bottom px-4 py-3 d-flex justify-content-between align-items-center">
        <div className="d-flex align-items-center gap-3">
          <img
            src={glasses}
            width="32"
            alt="logo"
          />
          <span className="fs-5 fw-bold">FocusNow</span>
        </div>
        <div
          ref={userCircleRef}
          className="rounded-circle bg-secondary"
          style={{ width: '40px', height: '40px', cursor: 'pointer' }}
          onClick={handleUserClick}
        >
          {username ? username.charAt(0).toUpperCase() : ''}
        </div>
      </nav>

      {/* Content below navbar */}
      <div style={{ flex: 1, display: 'flex', overflow: 'hidden' }}>
        {/* Sidebar */}
        <div className="bg-light border-end p-3 text-start" style={{ width: '250px', overflowY: 'auto' }}>
          <h5>Proyectos</h5>
          <ul className="list-unstyled">
            {projects.map((project) => (
              <li
                key={project.id}
                className="mb-2 p-2"
                style={{ cursor: 'pointer' }}
                onClick={() => handleProjectClick(project)}
              >
                {project.name}
              </li>
            ))}
          </ul>
        </div>

        {/* Main content */}
        <div className="d-flex flex-column flex-grow-1" style={{ overflowY: 'auto' }}>
          {/* Create Project button */}
          <div className="px-4 py-3 border-bottom d-flex justify-content-end">
            <button className="btn btn-primary" onClick={handleOpenCreateModal}>
              + Crear proyecto
            </button>
          </div>

          {/* Project cards */}
          <div className="row g-4 px-4 py-3">
            {projects.map((project) => (
              <div key={project.id} className="col-12 col-md-6 col-lg-4">
                <div
                  className="card shadow-sm"
                  style={{ cursor: 'pointer' }}
                  onClick={() => handleProjectClick(project)}
                >
                  <div className="card-body d-flex align-items-center gap-3">
                    <div
                      className="bg-success text-white rounded fw-bold d-flex align-items-center justify-content-center"
                      style={{ width: '40px', height: '40px' }}
                    >
                      {project.initials}
                    </div>
                    <h5 className="mb-0">{project.name}</h5>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Modal */}
      {showUserModal && (
        <div
          className="modal fade show"
          style={{ display: 'block' }}
          tabIndex="-1"
          role="dialog"
        >
          <div
            className="modal-dialog"
            role="document"
            style={{ marginTop: '60px', maxWidth: '300px', marginLeft: 'auto', marginRight: '20px' }}
          >
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Información del usuario</h5>
                <button
                  type="button"
                  className="btn-close"
                  aria-label="Close"
                  onClick={handleCloseModal}
                ></button>
              </div>
              <div className="modal-body">
                <p>Nombre: {username}</p>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" onClick={handleCloseModal}>
                  Cerrar
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {showCreateModal && (
        <div className="modal fade show" style={{ display: 'block' }} tabIndex="-1" role="dialog">
          <div className="modal-dialog" role="document" style={{ marginTop: '60px', maxWidth: '400px', marginLeft: 'auto', marginRight: '20px' }}>
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Crear nuevo proyecto</h5>
                <button type="button" className="btn-close" aria-label="Close" onClick={handleCloseCreateModal}></button>
              </div>
              <div className="modal-body">
                <form onSubmit={(e) => { e.preventDefault(); handleCreateProject(); }}>
                  <div className="mb-3">
                    <label htmlFor="projectName" className="form-label">Nombre del proyecto</label>
                    <input type="text" className="form-control" id="projectName" value={newProjectName} onChange={(e) => setNewProjectName(e.target.value)} required />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="projectDescription" className="form-label">Descripción</label>
                    <textarea className="form-control" id="projectDescription" rows="3" value={newDescription} onChange={(e) => setNewDescription(e.target.value)}></textarea>
                  </div>
                  <button type="submit" className="btn btn-primary">Crear proyecto</button>
                </form>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Home;
