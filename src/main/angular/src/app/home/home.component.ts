import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { KanbanService } from '../@core/service/kanban.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  boards: any = [];

  constructor(
    private kanbanService: KanbanService,
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
}
