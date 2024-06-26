import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { KanbanService } from '../../@core/service/kanban.service';
import { AppComponent } from '../../app.component';
import { TaskService } from '../../@core/service/task.service';
import { StringUtilsService } from '../../@core/utils/string-utils.service';
import { CreateATaskComponent } from '../../@core/component/create-a-task/create-a-task.component';
import { MatDialog } from '@angular/material/dialog';

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
    private taskService: TaskService,
    private dialog: MatDialog
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

  reduceString(string: string, length: number): string {
    return StringUtilsService.reduceString(string, length);
  }

  popupCreateTask(): void {
    this.dialog.open(CreateATaskComponent, { data: { kanbanName: this.name } }).afterClosed().subscribe(() => {
      this.updateTasks();
    });
  }
}
