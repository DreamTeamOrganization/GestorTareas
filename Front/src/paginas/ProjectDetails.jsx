import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Navbar from './Navbar';
import General from './General';
import List from './List';
import Kanban from './Kanban';

const ProjectDetails = () => {
  const [projectData, setProjectData] = useState(null);
  const [members, setMembers] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [activeSection, setActiveSection] = useState('general'); //utilizara general o lista
  const [viewsOpen, setViewsOpen] = useState(false);
  const [showTaskModal, setShowTaskModal] = useState(false);
  const [taskToEdit, setTaskToEdit] = useState({
    id: null,
    project: '',
    parentTask: null,
    taskType: '',
    title: '',
    description: '',
    user: null,
    priority: '',
    startDate: '',
    endDate: '',
    taskStatus: '',
  });
  const [highlightedTaskId, setHighlightedTaskId] = useState(null);

  const storeToken = localStorage.getItem('token');
  const storeUsername = localStorage.getItem('username');

  const location = useLocation();
  const navigate = useNavigate();
  const idProyecto = location.state?.idProyecto;

  //Obtener datos del proyecto
  const fetchProjectData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/projects/${idProyecto}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${storeToken}`,
          'Content-Type': 'application/json',
        },
      });
      if (response.ok) {
        const data = await response.json();
        setProjectData(data);
      } else {
        console.error('Error al obtener los datos del proyecto');
      }
    } catch (error) {
      console.error('Error en la petición:', error);
    }
  };

  //Obtener datos de los miembros del proyecto
  const fetchMembers = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/projects/${idProyecto}/with-users`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${storeToken}`,
          'Content-Type': 'application/json',
        },
      });
      if (response.ok) {
        const data = await response.json();
        setMembers(Array.isArray(data.users) ? data.users : []);
      } else {
        console.error('Error al obtener los miembros');
      }
    } catch (error) {
      console.error('Error en la petición:', error);
    }
  };

  //Crear un task
  const createTask = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/tasks/add', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${storeToken}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          taskType: taskToEdit.taskType,
          title: taskToEdit.title,
          description: taskToEdit.description,
          user: taskToEdit.user,
          priority: taskToEdit.priority,
          startDate: taskToEdit.startDate,
          endDate: taskToEdit.endDate,
          taskStatus: taskToEdit.taskStatus,
          project: idProyecto,
        }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        alert('Error al crear la tarea: ' + errorText);
        throw new Error('Error al crear la tarea');
      }

      const data = await response.json();
      const idTask = data.id; // Obtener el id de la tarea creada
      alert('Tarea creada exitosamente');
      if (idTask) {
        setHighlightedTaskId(idTask);
        // Ver la tarea que se acaba de crear con idTask
        await getTaskById(idTask);
      }
      fetchTasks();
    } catch (error) {
      alert(error.message);
    }
  };

  //Obtener la tarea que se acaba de crear con idTask
 const getTaskById = async (idTask) => {
    try {
      const response = await fetch(`http://localhost:8080/api/tasks/${idTask}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${storeToken}`,
          'Content-Type': 'application/json',
        },
      });
      if (response.ok) {
        const data = await response.json();
        setTaskToEdit({
          id: data.id,
          project: data.project,
          parentTask: data.parentTask || null,
          taskType: data.taskType,
          title: data.title,
          description: data.description,
          user: data.user,
          priority: data.priority,
          startDate: data.startDate,
          endDate: data.endDate,
          taskStatus: data.taskStatus,
        });
        setShowTaskModal(true);
      } else {
        alert('Error al obtener la tarea creada');
      }
    } catch (error) {
      alert('Error en la petición para obtener la tarea creada');
    }
  };

  //Obtener los tasks que tiene un proyecto
 const fetchTasks = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/tasks/project/${idProyecto}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${storeToken}`,
          'Content-Type': 'application/json',
        },
      });
      if (response.ok) {
        const data = await response.json();
        setTasks(Array.isArray(data) ? data : []);
      } else {
        console.error('Error al obtener las tareas');
      }
    } catch (error) {
      console.error('Error en la petición:', error);
    }
  };

  //Editar el task
  const saveTask = async () => {
    if (taskToEdit.id) {
      // Editar tarea existente
      try {
        const response = await fetch(`http://localhost:8080/api/tasks/${taskToEdit.id}`, {
          method: 'PUT',
          headers: {
            'Authorization': `Bearer ${storeToken}`,
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            project: idProyecto,
            parentTask: taskToEdit.parentTask,
            taskType: taskToEdit.taskType,
            title: taskToEdit.title,
            description: taskToEdit.description,
            user: taskToEdit.user,
            priority: taskToEdit.priority,
            startDate: taskToEdit.startDate,
            endDate: taskToEdit.endDate,
            taskStatus: taskToEdit.taskStatus,
          }),
        });

        if (!response.ok) {
          throw new Error('Error al editar la tarea');
        }

        alert('Tarea editada exitosamente');
        closeTaskModal();
        fetchTasks();
      } catch (error) {
        alert(error.message);
      }
    } else {
      // Crear nueva tarea
      await createTask();
    }
  };

  //Borrar task
  const deleteTask = async (taskId) => {
    const confirmacion = window.confirm('¿Estás seguro de que quieres eliminar esta tarea?');
    if (!confirmacion) return;

    try {
      const response = await fetch(`http://localhost:8080/api/tasks/${taskId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${storeToken}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('Error al eliminar la tarea');
      }

      alert('Tarea eliminada exitosamente');
      fetchTasks();
    } catch (error) {
      alert(error.message);
    }
  };

  useEffect(() => {
    if (!idProyecto) {
      navigate('/home');
      return;
    }

    fetchProjectData();
    fetchMembers();
    fetchTasks();
  }, [idProyecto, navigate, storeToken]);

  const onBack = () => {
    navigate('/home');
  };

  const openTaskModal = (task = null) => {
    if (task) {
      setTaskToEdit({
        id: task.id,
        project: task.project,
        parentTask: task.parentTask || null,
        taskType: task.taskType,
        title: task.title,
        description: task.description,
        user: task.user,
        priority: task.priority,
        startDate: task.startDate,
        endDate: task.endDate,
        taskStatus: task.taskStatus,
      });
      setShowTaskModal(true);
    } else {
      setTaskToEdit({
        id: null,
        project: idProyecto,
        parentTask: null,
        taskType: '',
        title: '',
        description: '',
        user: '',
        priority: '',
        startDate: '',
        endDate: '',
        taskStatus: '',
      });
      setShowTaskModal(true);
    }
  };

  const closeTaskModal = () => {
    setShowTaskModal(false);
    setHighlightedTaskId(null);
  };

  const handleTaskChange = (e) => {
    const { name, value } = e.target;
    setTaskToEdit(prev => ({ ...prev, [name]: value }));
  };


  return (
    <>
      <Navbar username={storeUsername} />

      <div className="d-flex" style={{ height: '100vh' }}>
        {/* Sidebar */}
        <div className="bg-light border-end p-3" style={{ width: '250px', overflowY: 'auto' }}>
          <h5>{projectData?.name || 'Proyecto'}</h5>

          <div>
            <button
              className={`btn btn-link text-start w-100 ${activeSection === 'general' ? 'fw-bold' : ''}`}
              onClick={() => setActiveSection('general')}
            >
              General
            </button>
          </div>

          <div>
            <button
              className="btn btn-link text-start w-100 d-flex justify-content-between align-items-center"
              onClick={() => setViewsOpen(!viewsOpen)}
            >
              Vistas
              <span>{viewsOpen ? '▲' : '▼'}</span>
            </button>
            {viewsOpen && (
              <ul className="list-unstyled ps-3">
                <li>
                  <button
                    className={`btn btn-link text-start w-100 ${activeSection === 'lista' ? 'fw-bold' : ''}`}
                    onClick={() => setActiveSection('lista')}
                  >
                    lista
                  </button>
                </li>

                <li>
                  <button
                    className={`btn btn-link text-start w-100 ${activeSection === 'kanban' ? 'fw-bold' : ''}`}
                    onClick={() => setActiveSection('kanban')}
                  >
                    kanban
                  </button>
                </li>
              </ul>
            )}
          </div>
        </div>

        {/* Información general */}
        <div className="p-4 flex-grow-1" style={{ overflowY: 'auto' }}>
          <div className="mb-3 d-flex justify-content-start">
            <button className="btn btn-secondary" onClick={onBack}>
              Volver a proyectos
            </button>
          </div>

          {activeSection === 'general' && (
            <General projectData={projectData} members={members} />
          )}

          {activeSection === 'lista' && (
            <List
              tasks={tasks}
              members={members}
              openTaskModal={openTaskModal}
              deleteTask={deleteTask}
              highlightedTaskId={highlightedTaskId}
            />
          )}

          {activeSection === 'kanban' && (
            <Kanban/>
          )}

          {showTaskModal && (
            <div className="modal fade show" style={{ display: 'block' }} tabIndex="-1" role="dialog">
              <div className="modal-dialog" role="document" style={{ maxWidth: '600px', marginTop: '60px', marginLeft: 'auto', marginRight: 'auto' }}>
                <div className="modal-content">
                  <div className="modal-header">
                    <h5 className="modal-title">{taskToEdit.id ? 'Editar Tarea' : 'Crear Tarea'}</h5>
                    <button type="button" className="btn-close" aria-label="Close" onClick={closeTaskModal}></button>
                  </div>
                  <div className="modal-body">
                    <form onSubmit={(e) => { e.preventDefault(); saveTask(); }}>
                      <div className="mb-3">
                        <label htmlFor="title" className="form-label">Título</label>
                        <input
                          type="text"
                          className="form-control"
                          id="title"
                          name="title"
                          value={taskToEdit.title}
                          onChange={handleTaskChange}
                          required
                        />
                      </div>
                      <div className="mb-3">
                        <label htmlFor="description" className="form-label">Descripción</label>
                        <textarea
                          className="form-control"
                          id="description"
                          name="description"
                          rows="3"
                          value={taskToEdit.description}
                          onChange={handleTaskChange}
                        ></textarea>
                      </div>
                      <div className="mb-3">
                        <label htmlFor="user" className="form-label">Usuario</label>
                        <select
                          className="form-select"
                          id="user"
                          name="user"
                          value={taskToEdit.user}
                          onChange={handleTaskChange}
                          required
                        >
                          <option value="">Seleccione un usuario</option>
                          {members.map((member) => (
                          <option key={member.id} value={member.id}>{member.username}</option>
                          ))}
                        </select>
                      </div>
                      <div className="mb-3">
                        <label htmlFor="priority" className="form-label">Prioridad</label>
                        <select
                          className="form-select"
                          id="priority"
                          name="priority"
                          value={taskToEdit.priority}
                          onChange={handleTaskChange}
                          required
                        >
                          <option value="">Seleccione prioridad</option>
                          <option value="Baja">Baja</option>
                          <option value="Media">Media</option>
                          <option value="Alta">Alta</option>
                        </select>
                      </div>
                      <div className="mb-3">
                        <label htmlFor="taskType" className="form-label">Tipo de Tarea</label>
                        <input
                          type="text"
                          className="form-control"
                          id="taskType"
                          name="taskType"
                          value={taskToEdit.taskType}
                          onChange={handleTaskChange}
                        />
                      </div>
                      <div className="mb-3">
                        <label htmlFor="taskStatus" className="form-label">Estado de la Tarea</label>
                        <input
                          type="text"
                          className="form-control"
                          id="taskStatus"
                          name="taskStatus"
                          value={taskToEdit.taskStatus}
                          onChange={handleTaskChange}
                        />
                      </div>
                      <div className="mb-3">
                        <label htmlFor="startDate" className="form-label">Fecha de Inicio</label>
                        <input
                          type="date"
                          className="form-control"
                          id="startDate"
                          name="startDate"
                          value={taskToEdit.startDate}
                          onChange={handleTaskChange}
                        />
                      </div>
                      <div className="mb-3">
                        <label htmlFor="endDate" className="form-label">Fecha de Fin</label>
                        <input
                          type="date"
                          className="form-control"
                          id="endDate"
                          name="endDate"
                          value={taskToEdit.endDate}
                          onChange={handleTaskChange}
                        />
                      </div>
                      <button type="submit" className="btn btn-primary">{taskToEdit.id ? 'Guardar Cambios' : 'Crear Tarea'}</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default ProjectDetails;
