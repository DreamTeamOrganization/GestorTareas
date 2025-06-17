class UtilTaskStatusByProject {

    constructor() {
        this.token = localStorage.getItem('token');
    }

    async getTaksStatusByProject(projectId) {
        try {
            const response = await fetch(`http://localhost:8080/api/taskStatusProject/${projectId}`, {
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

export default UtilTaskStatusByProject;