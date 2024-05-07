import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../../app.component';
import { ActivatedRoute } from '@angular/router';
import { KanbanService } from '../../@core/service/kanban.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent implements OnInit {
  routed_name: string = ''
  name: string = ''
  description: string = ''
  color: string = ''

  constructor(
    private routes: ActivatedRoute,
    private kanbanService: KanbanService
  ) { }

  ngOnInit(): void {
    this.routed_name = this.routes.snapshot.paramMap.get('name') || ''
    this.name = this.routed_name

    this.kanbanService.getBoard(this.name).subscribe({
      next: kanban => {
        AppComponent.title = this.name + ' - Settings'
        AppComponent.rightButtonText = "⬅️"
        AppComponent.rightButtonRedirect = '/kanban/' + this.name
        AppComponent.leftButtonText = ''
        AppComponent.leftButtonRedirect = ''
        const body = kanban as { [key: string]: string };
        this.description = body["description"] ?? '';
        this.color = body["boxColors"] ?? '';
      },
      error: error => {
        window.location.href = '/'
      }
    });

    AppComponent.rightButtonText = "⬅️"
    AppComponent.rightButtonRedirect = '/'
    AppComponent.leftButtonText = "⚙️"
    AppComponent.leftButtonRedirect = `/kanban/${this.name}/settings`
  }

  changeEvent(event: any): void {
    switch (event.target.name) {
      case 'name':
        this.name = event.target.value
        break
      case 'description':
        this.description = event.target.value
        break
      case 'color':
        this.color = event.target.value.replace("#", "")
        break
      default:
        window.location.reload()
        break
    }
    this.update()
  }

  delete(): void {
    this.kanbanService.deleteBoard(this.name).subscribe({
      next: (response) => {
        window.location.href = '/'
      },
      error: error => {
        window.location.href = '/'
      }
    });
  }

  update(): void {
    this.kanbanService.updateBoard(this.routed_name, this.name, this.description, this.color).subscribe({
      next: (response) => {
        if (this.routed_name !== this.name) {
          window.location.href = '/kanban/' + this.name + '/settings'
        }
      },
      error: error => {
        window.location.href = '/'
      }
    });
  }
}
