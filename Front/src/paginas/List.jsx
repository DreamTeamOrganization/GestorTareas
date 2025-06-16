import React from 'react';

const safeRender = (field) => {
  if (typeof field === 'object' && field !== null) {
    return field.name || field.username || field.title || '';
  }
  return field || '';
};

const List = ({ tasks, members, openTaskModal, deleteTask, highlightedTaskId }) => {
  return (
    <div>
      <h3>Lista de Tareas</h3>
      <button className="btn btn-primary mb-3" onClick={() => openTaskModal()}>
        + Crear Tarea
      </button>
      {!Array.isArray(tasks) || tasks.length === 0 ? (
        <p>No hay tareas en este proyecto.</p>
      ) : (
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Orden</th>
              <th>ID Proyecto</th>
              <th>Estado</th>
              <th>Título</th>
              <th>Tipo de Tarea</th>
              <th>Descripción</th>
              <th>Usuario</th>
              <th>Prioridad</th>
              <th>Fecha Inicio</th>
              <th>Fecha Fin</th>
              <th>Estado Task</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {tasks.map((task, index) => (
              <tr
                key={task.id}
                style={task.id === highlightedTaskId ? { backgroundColor: '#d1e7dd' } : {}}
              >
                <td>{index + 1}</td>
                <td>{safeRender(task.project)}</td>
                <td>{safeRender(task.taskStatus)}</td>
                <td>{safeRender(task.title)}</td>
                <td>{safeRender(task.taskType)}</td>
                <td>{safeRender(task.description)}</td>
                <td>{safeRender(task.user)}</td>
                <td>{safeRender(task.priority)}</td>
                <td>{safeRender(task.startDate)}</td>
                <td>{safeRender(task.endDate)}</td>
                <td>{safeRender(task.taskStatus)}</td>
                <td>
                  <button className="btn btn-sm btn-warning me-2" onClick={() => openTaskModal(task)}>Editar</button>
                  <button className="btn btn-sm btn-danger" onClick={() => deleteTask(task.id)}>Eliminar</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default List;
