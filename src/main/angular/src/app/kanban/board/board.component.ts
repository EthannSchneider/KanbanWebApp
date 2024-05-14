import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../../app.component';
import { KanbanService } from '../../@core/service/kanban.service';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../../@core/service/task.service';
import { StringUtilsService } from '../../@core/utils/string-utils.service';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss'
})
export class BoardComponent implements OnInit{
  name: string = ''
  backlogRedirect: string = ''
  boardRedirect: string = ''
  lastDraggedOver: any = null
  baseDraggedOver: any = null
  todoTasks: any = []
  inProgressTasks: any = []
  toTestTasks: any = []
  doneTasks: any = []

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
        AppComponent.title = StringUtilsService.reduceString(this.name, 20)
        this.updateTasks()
      },
      error: error => {
        window.location.href = '/'
      }
    });
  }

  updateTasks() {
    this.todoTasks = []
    this.inProgressTasks = []
    this.toTestTasks = []
    this.doneTasks = []
    this.taskService.getTasks(this.name).subscribe({
      next: tasks => {
        const body = tasks as [{ [key: string]: string }];
        for (let task of body) {
          if (task["status"] == 'TODO') {
            this.todoTasks.push(task)
          } else if (task["status"] == 'IN_PROGRESS') {
            this.inProgressTasks.push(task)
          } else if (task["status"] == 'TO_TEST') {
            this.toTestTasks.push(task)
          } else if (task["status"] == 'DONE') {
            this.doneTasks.push(task)
          }
        }
      },
      error: error => {
        window.location.href = '/'
      }
    });
  }

  onDragStart(event: any) {
    event.target.classList.add("dragging");
    this.baseDraggedOver = event.target.parentElement;
  }

  onDragEnd(event: any) {
    event.target.classList.remove("dragging");
    if (this.lastDraggedOver.id != this.baseDraggedOver.id && event.target.id != this.lastDraggedOver.id) {
      const task_name = event.target.innerHTML.split('<')[1].split('>')[1]
      this.taskService.updateTask(this.name, task_name, '', '', '', '', this.lastDraggedOver.id.replace("-tasks", "")).subscribe({
        next: task => {
          this.updateTasks()
        },
        error: error => {
          window.location.href = '/'
        }
      });
    }
  }

  onDragOver(event: any) {
    event.preventDefault();
    this.lastDraggedOver = event.target;
  }

  gotoTasksDetails(taskName: string) {
    window.location.href = `/kanban/${this.name}/task/${taskName}`
  }

  reduceString(string: string, length: number): string {
    return StringUtilsService.reduceString(string, length);
  }
}
