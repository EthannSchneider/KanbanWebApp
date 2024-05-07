import { Component, Input, OnInit, input } from '@angular/core';
import { KanbanService } from '../../service/kanban.service';
import { MatDialogRef } from '@angular/material/dialog';
import { UntypedFormControl, UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-create-a-board',
  templateUrl: './create-a-board.component.html',
  styleUrl: './create-a-board.component.scss'
})
export class CreateABoardComponent {
  boardName: string = '';
  boardDescription: string = '';
  boardColor: string = '#000000';
  error: string = '';
  constructor(
    private kanbanService: KanbanService,
    public dialogRef: MatDialogRef<CreateABoardComponent>
  ) { }

  updateBoardName(event: any) {
    this.boardName = event.target.value;
  }

  updateBoardDescription(event: any) {
    this.boardDescription = event.target.value;
  }

  updateBoardColor(event: any) {
    this.boardColor = event.target.value;
  }

  submit() {
    if (this.boardName === '') {
      return;
    }
    this.kanbanService.createBoard(this.boardName, this.boardDescription, this.boardColor.replace('#', '')).subscribe({
      next: board => {
        this.dialogRef.close();
      },
      error: err => {
        if(err.status === 400) {
          this.error = "Board name already exists. Please try another name."
        }else {
          this.error = "Failed to create board. Please try again."
          console.error(err);
        }
      }
    });
  }

  close() {
    this.dialogRef.close();
  }
}
