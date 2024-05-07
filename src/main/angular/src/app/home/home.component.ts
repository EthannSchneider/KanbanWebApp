import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { KanbanService } from '../@core/service/kanban.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateABoardComponent } from '../@core/component/create-a-board/create-a-board.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  boards: any = [];

  constructor(
    private kanbanService: KanbanService,
    private dialog: MatDialog
  ) { }
  
  ngOnInit(): void {
    AppComponent.title = 'Kanban Boards';

    this.kanbanService.getBoards().subscribe((boards: any) => {
      this.boards = boards;
    });
  }

  generateColor(color: string): string {
    return "background-color: #" + color + ";"
  }

  popupCreateBoard(): void {
    this.dialog.open(CreateABoardComponent);
    this.dialog.afterAllClosed.subscribe(() => {
      this.kanbanService.getBoards().subscribe((boards: any) => {
        this.boards = boards;
      });
    });
  }
}
