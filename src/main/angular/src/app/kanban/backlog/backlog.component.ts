import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { KanbanService } from '../../@core/service/kanban.service';
import { AppComponent } from '../../app.component';

@Component({
  selector: 'app-backlog',
  templateUrl: './backlog.component.html',
  styleUrl: './backlog.component.scss'
})
export class BacklogComponent implements OnInit{
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
