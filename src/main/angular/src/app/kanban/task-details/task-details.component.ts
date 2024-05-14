import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../../app.component';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../../@core/service/task.service';

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.scss'
})
export class TaskDetailsComponent implements OnInit {
  kanbanName: string = ''
  taskName: string = '' 
  taskRoutedName: string = '' 
  taskDescription: string = ''
  taskAssignee: string = ''
  taskStatus: string = ''
  taskTimeToRelease: string = ''

  constructor(
    private routes: ActivatedRoute,
    private taskService: TaskService,
  ) { }

  ngOnInit(): void {
    this.kanbanName = this.routes.snapshot.paramMap.get('name') || ''
    this.taskRoutedName = this.routes.snapshot.paramMap.get('taskName') || ''
    this.taskName = this.taskRoutedName

    AppComponent.title = this.taskName + ' - Details'
    AppComponent.rightButtonText = "⬅️"
    AppComponent.rightButtonRedirect = '/kanban/' + this.kanbanName + '/board'
    AppComponent.leftButtonText = ''
    AppComponent.leftButtonRedirect = ''

    this.taskService.getTask(this.kanbanName, this.taskName).subscribe({
      next: task => {
        const body = task as { [key: string]: string };
        this.taskDescription = body['description']
        this.taskAssignee = body['assignee']
        this.taskStatus = body['status']
        this.taskTimeToRelease = body['timeToRelease']
      },
      error: error => {
        window.location.href = '/kanban/' + this.kanbanName + '/board'
      }
    });
  }

  changeEvent(event: any): void {
    switch (event.target.name) {
      case 'name':
        this.taskName = event.target.value
        break
      case 'description':
        this.taskDescription = event.target.value
        break
      case 'assignee':
        this.taskAssignee = event.target.value
        break
      case 'status':
        this.taskStatus = event.target.value
        break
      case 'timeToRelease':
        this.taskTimeToRelease = event.target.value
        break
      default:
        window.location.reload()
        break
    }
    this.update()
  }

  update(): void {
    this.taskService.updateTask(this.kanbanName, this.taskRoutedName, this.taskName, this.taskAssignee, this.taskDescription, this.taskTimeToRelease, this.taskStatus).subscribe({
      next: (response) => {
        if (this.taskRoutedName !== this.taskName) {
          window.location.href = '/kanban/' + this.kanbanName + '/task/' + this.taskName
        }
      },
      error: error => {
        window.location.href = '/'
      }
    });
  }
}