import React from 'react';
import glasses from '../assets/glasses.svg';
import 'bootstrap/dist/css/bootstrap.min.css';

const Home = () => {
  const projects = [
    { id: 1, name: 'Proyecto 1', initials: 'P1' },
    { id: 2, name: 'Proyecto 2', initials: 'P2' },
  ];

  const handleProjectClick = (project) => {
    alert(`Project selected: ${project.name}`);
  };

  const handleCreateProject = () => {
    alert('Create Project button clicked');
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
        <div className="rounded-circle bg-secondary" style={{ width: '40px', height: '40px' }}></div>
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
            <button className="btn btn-primary" onClick={handleCreateProject}>
              + Crear Proyecto
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
    </div>
  );
};

export default Home;
