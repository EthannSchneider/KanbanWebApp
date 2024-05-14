import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { KanbanService } from '../../@core/service/kanban.service';
import { AppComponent } from '../../app.component';
import { TaskService } from '../../@core/service/task.service';

@Component({
  selector: 'app-backlog',
  templateUrl: './backlog.component.html',
  styleUrl: './backlog.component.scss'
})
export class BacklogComponent implements OnInit{
  backlogTasks: any = []
  name: string = ''
  backlogRedirect: string = ''
  boardRedirect: string = ''
  
  constructor(
    private route: ActivatedRoute,
    private kanbanService: KanbanService,
    private taskService: TaskService
  ) { }

  ngOnInit(): void {
    this.name = this.route.snapshot.paramMap.get('name') || ''

    AppComponent.rightButtonText = "⬅️"
    AppComponent.rightButtonRedirect = '/'
    AppComponent.leftButtonText = "⚙️"
    AppComponent.leftButtonRedirect = `/kanban/${this.name}/settings`

    this.boardRedirect = `/kanban/${this.name}/board`
    this.backlogRedirect = `/kanban/${this.name}/backlog`

    this.kanbanService.getBoard(this.name).subscribe({
      next: kanban => {
        AppComponent.title = this.name
        this.updateTasks();
      },
      error: error => {
        window.location.href = '/'
      }
    });
  }

  updateTasks() {
    this.backlogTasks = []
    this.taskService.getTasks(this.name).subscribe({
      next: tasks => {
        const body = tasks as [{ [key: string]: string }];
        for (let task of body) {
          if (task["status"] == 'BACKLOG') {
            this.backlogTasks.push(task)
          }
        }
      },
      error: error => {
        window.location.href = '/'
      }
    });
  }

  gotoTasksDetails(taskName: string) {
    window.location.href = `/kanban/${this.name}/task/${taskName}`
  }
}
