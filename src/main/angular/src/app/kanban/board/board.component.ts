import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../../app.component';
import { KanbanService } from '../../@core/service/kanban.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss'
})
export class BoardComponent implements OnInit{
  name: string = ''
  backlogRedirect: string = ''
  boardRedirect: string = ''
  
  constructor(
    private route: ActivatedRoute,
    private kanbanService: KanbanService
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
      },
      error: error => {
        window.location.href = '/'
      }
    });
  }
}
