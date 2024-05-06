import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class KanbanService {

  constructor(
    private http: HttpClient,
  ) { }

  getBoards() {
    return this.http.get('/api/kanban');
  }

  getBoard(name: string) {
    return this.http.get(`/api/kanban/${name}`);
  }

  createBoard(name: string, description: string, boxColors: string) {
    return this.http.post('/api/kanban', { name, description, boxColors });
  }

  deleteBoard(name: string) {
    return this.http.delete(`/api/kanban/${name}`);
  }

  updateBoard(name: string, description: string, boxColors: string) {
    return this.http.patch(`/api/kanban/${name}`, { name, description, boxColors });
  }
}
