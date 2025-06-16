import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from './Navbar';

const Home = () => {
  const [projects, setProjects] = useState([]);
  const [idUser, setUserId] = useState([]);
  const [username, setUsername] = useState(null);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [newProjectName, setNewProjectName] = useState('');
  const [newDescription, setNewDescription] = useState('');
  const [selectedProject, setSelectedProject] = useState(null);
  const [openMenuProjectId, setOpenMenuProjectId] = useState(null);
  const navigate = useNavigate();
  const [showEditModal, setShowEditModal] = useState(false);
  const [projectToEdit, setProjectToEdit] = useState({ idProyecto: null, name: '', description: '' });

  const storeUserId = localStorage.getItem("idUser");
  const storeUsername = localStorage.getItem("username");
  const storeToken = localStorage.getItem("token");

  const fetchProjects = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/projects/user/${storeUserId}`, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${storeToken}`,
          "Content-Type": "application/json"
        },
      });
      const result = await response.json();

      // Fetch individual project details to get description
      const projectsWithDescription = await Promise.all(result.map(async (project) => {
        const res = await fetch(`http://localhost:8080/api/projects/${project.id}`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${storeToken}`,
            "Content-Type": "application/json"
          },
        });
        const projectDetails = await res.json();
        return {
          ...project,
          description: projectDetails.description || '',
        };
      }));

      setProjects(projectsWithDescription);
    } catch (error) {
      console.error("Error fetching projects:", error);
    }
  };

  useEffect(() => {
    fetchProjects();

    if (storeUserId) {
      setUserId(storeUserId);
    }

    if (storeUsername) {
      setUsername(storeUsername);
    }

    if (!storeToken) {
      navigate('/')
    }
  }, [storeUserId, storeToken, navigate]);

  const openEditModal = (project) => {
    setProjectToEdit({
      idProyecto: project.id,
      name: project.name,
      description: project.description,
    });
    setShowEditModal(true);
    setOpenMenuProjectId(null);
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setProjectToEdit(prev => ({ ...prev, [name]: value }));
  };

  const handleSaveEdit = async () => {
    await editarProyecto(projectToEdit.idProyecto, projectToEdit.name, projectToEdit.description);
    setShowEditModal(false);
  };

  const editarProyecto = async (idProyecto, name, description) => {
    try {
      const response = await fetch(`http://localhost:8080/api/projects/${idProyecto}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${storeToken}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name, description }),
      });

      if (!response.ok) {
        throw new Error('Error al editar el proyecto');
      }

      alert('Proyecto editado exitosamente');
      fetchProjects();
    } catch (error) {
      alert(error.message);
    }
    setOpenMenuProjectId(null);
  }

  const eliminarProyecto = async (idProyecto) => {
    const confirmacion = window.confirm('¿Estás seguro de que quieres eliminar este proyecto?');
    if (!confirmacion) return;

    try {
      const response = await fetch(`http://localhost:8080/api/projects/${idProyecto}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${storeToken}`,
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        throw new Error('Error al eliminar el proyecto');
      }
      alert('Proyecto eliminado exitosamente');
      setOpenMenuProjectId(null);
      fetchProjects();
    } catch (error) {
      alert(error.message);
    }
  };

  const handleProjectClick = (project) => {
    navigate(`/projectDetails`, { state: { idProyecto: project.id } });
  };

  const handleBackToList = () => {
    setSelectedProject(null);
  };

  const handleCreateProject = async () => {
    const response = await fetch(`http://localhost:8080/api/projects/add`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${storeToken}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ name: newProjectName, description: newDescription, idUser: idUser })
    });
    if (!response.ok) throw new Error("Error al crear el proyecto");

    alert(newDescription, idUser);
    alert('Proyecto creado exitosamente');
    setShowCreateModal(false);
    setNewProjectName("");
    setNewDescription("");

    fetchProjects();
  };

  const handleOpenCreateModal = () => setShowCreateModal(true);
  const handleCloseCreateModal = () => setShowCreateModal(false);

  return (
    <>
      <Navbar username={username} />

      <div style={{ height: '100vh', width: '100%', display: 'flex', flexDirection: 'column' }}>
        <div style={{ flex: 1, display: 'flex', overflow: 'hidden' }}>
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

          <div className="d-flex flex-column flex-grow-1" style={{ overflowY: 'auto' }}>
            <>
              <div className="px-4 py-3 border-bottom d-flex justify-content-end">
                <button className="btn btn-primary" onClick={handleOpenCreateModal}>
                  + Crear proyecto
                </button>
              </div>

              <div className="row g-4 px-4 py-3">
                {projects.length === 0 ? (
                  <p>No hay proyectos para mostrar.</p>
                ) : (
                  projects.map((project) => (
                    <div key={project.id} className="col-12 col-md-6 col-lg-4">
                      <div
                        className="card shadow-sm"
                        style={{ cursor: 'pointer', position: 'relative' }}
                        onClick={() => handleProjectClick(project)}
                      >
                        <div className="card-body d-flex align-items-center gap-3" style={{ flexGrow: 1 }}>
                          <div
                            className="bg-success text-white rounded fw-bold d-flex align-items-center justify-content-center"
                            style={{ width: '40px', height: '40px' }}
                          >
                            {project.initials}
                          </div>
                          <h5 className="mb-0 flex-grow-1">{project.name}</h5>
                          <div
                            style={{ cursor: 'pointer', padding: '0 8px', userSelect: 'none' }}
                            onClick={(e) => {
                              e.stopPropagation();
                              setOpenMenuProjectId(openMenuProjectId === project.id ? null : project.id);
                            }}
                          >
                            &#8942;
                          </div>
                        </div>
                        {openMenuProjectId === project.id && (
                          <div
                            style={{
                              position: 'absolute',
                              top: '40px',
                              right: '10px',
                              backgroundColor: 'white',
                              border: '1px solid #ccc',
                              borderRadius: '4px',
                              boxShadow: '0 2px 8px rgba(0,0,0,0.15)',
                              zIndex: 1000,
                            }}
                            onClick={(e) => e.stopPropagation()}
                          >
                            <div
                              style={{ padding: '8px 12px', cursor: 'pointer' }}
                              onClick={() => openEditModal(project)}
                            >
                              Editar
                            </div>
                            <div
                              style={{ padding: '8px 12px', cursor: 'pointer', color: 'red' }}
                              onClick={() => eliminarProyecto(project.id)}
                            >
                              Eliminar
                            </div>
                          </div>
                        )}
                      </div>
                    </div>
                  ))
                )}
              </div>
            </>
          </div>
        </div>
      </div>

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

      {showEditModal && (
        <div className="modal fade show" style={{ display: 'block' }} tabIndex="-1" role="dialog">
          <div className="modal-dialog" role="document" style={{ marginTop: '60px', maxWidth: '400px', marginLeft: 'auto', marginRight: '20px' }}>
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Editar Proyecto</h5>
                <button type="button" className="btn-close" aria-label="Close" onClick={() => setShowEditModal(false)}></button>
              </div>
              <div className="modal-body">
                <div className="mb-3">
                  <label htmlFor="editName" className="form-label">Nombre</label>
                  <input
                    type="text"
                    className="form-control"
                    id="editName"
                    name="name"
                    value={projectToEdit.name}
                    onChange={handleEditChange}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="editDescription" className="form-label">Descripción</label>
                  <textarea
                    className="form-control"
                    id="editDescription"
                    name="description"
                    rows="3"
                    value={projectToEdit.description}
                    onChange={handleEditChange}
                  ></textarea>
                </div>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" onClick={() => setShowEditModal(false)}>Cancelar</button>
                <button type="button" className="btn btn-primary" onClick={handleSaveEdit}>Guardar</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Home;
