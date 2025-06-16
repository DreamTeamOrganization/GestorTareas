import React, { useRef, useState } from 'react';
import glasses from '../assets/glasses.svg';

const Navbar = ({ username }) => {
  const userCircleRef = useRef(null);
  const [showUserModal, setShowUserModal] = useState(false);
  const [modalTop, setModalTop] = useState(0);


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

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('idUser');
    localStorage.removeItem('username');
    setShowUserModal(false);
    window.location.href = '/'; // Redirigir a la página de ingreso
  };

  return (
    <>
      {/* Navbar */}
      <nav className="navbar navbar-light bg-white border-bottom px-4 py-3 d-flex justify-content-between align-items-center">
        <div className="d-flex align-items-center gap-3">
          <img src={glasses} width="32" alt="logo" />
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

        {/* Modal de usuario */}
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
                  <button type="button" className="btn btn-danger ms-2" onClick={handleLogout}>
                    Cerrar sesión
                  </button>
                </div>
              </div>
            </div>
          </div>
        )}
      </nav>
    </>
  );
};

export default Navbar;
