import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TaskService } from '../../service/task.service';

@Component({
  selector: 'app-create-a-task',
  templateUrl: './create-a-task.component.html',
  styleUrl: './create-a-task.component.scss'
})
export class CreateATaskComponent implements OnInit {
  kanbanName: string = '';
  taskName: string = '';
  taskDescription: string = '';
  taskAssignee: string = '';
  taskTimeToRelease: string = '';
  error: string = '';

  constructor(
    private taskService: TaskService,
    public dialogRef: MatDialogRef<CreateATaskComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.kanbanName = this.data.kanbanName;
  }

  updateTaskName(event: any) {
    this.taskName = event.target.value;
  }

  updateTaskDescription(event: any) {
    this.taskDescription = event.target.value;
  }

  updateTaskAssignee(event: any) {
    this.taskAssignee = event.target.value;
  }

  updateTaskTimeToRelease(event: any) {
    this.taskTimeToRelease = event.target.value;
  }

  submit() {
    if (this.taskName === '') {
      return;
    }
    this.taskService.createTask(this.kanbanName ,this.taskName, this.taskAssignee, this.taskDescription, this.taskTimeToRelease, "BACKLOG").subscribe({
      next: Task => {
        this.dialogRef.close();
      },
      error: err => {
        if(err.status === 409) {
          this.error = "Task name already exists. Please try another name."
        }else {
          this.error = "Failed to create Task. Please try again."
        }
      }
    });
  }

  close() {
    this.dialogRef.close();
  }
}
