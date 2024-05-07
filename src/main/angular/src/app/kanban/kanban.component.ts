import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';
import { KanbanService } from '../@core/service/kanban.service';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrl: './kanban.component.scss'
})
export class KanbanComponent implements OnInit {
  name: string = ''

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
