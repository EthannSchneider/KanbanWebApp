import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(
    private http: HttpClient,
  ) { }

    getTasks(kanbanName: string) {
        return this.http.get('/api/kanban/' + kanbanName + '/task');
    }

    getTask(kanbanName: string, taskName: string) {
        return this.http.get('/api/kanban/' + kanbanName + '/task/' + taskName);
    }

    createTask(kanbanName: string, name: string, assignee: string, description: string, timeToRelease: string, status: string) {
        return this.http.post('/api/kanban/' + kanbanName + '/task', { name, assignee, description, timeToRelease, status });
    }

    deleteTask(kanbanName: string, taskName: string) {
        return this.http.delete('/api/kanban/' + kanbanName + '/task/' + taskName);
    }

    updateTask(kanbanName: string, oldName: string, name: string, assignee: string, description: string, timeToRelease: string, status: string) {
        return this.http.patch('/api/kanban/' + kanbanName + '/task/' + oldName, { name, assignee, description, timeToRelease, status });
    }
}
