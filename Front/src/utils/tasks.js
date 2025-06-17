class UtilTask {

  constructor() {
    this.token = localStorage.getItem('token');
  }

  async getTaksByProject(projectId) {
    try {
      const response = await fetch(`http://localhost:8080/api/tasks/project/${projectId}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${this.token}`,
          'Content-Type': 'application/json',
        },
      });
      if (response.ok) {
        return response.json();
      } else {
        console.error('Error al obtener los datos');
      }
    } catch (error) {
      console.error('Error en la petición:', error);
    }
  };
}

export default UtilTask;