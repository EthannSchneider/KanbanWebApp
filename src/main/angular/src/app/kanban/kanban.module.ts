import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KanbanComponent } from './kanban.component';
import { KanbanRoutingModule } from './kanban-routing.module';
import { SettingsComponent } from './settings/settings.component';



@NgModule({
  declarations: [
    KanbanComponent,
    SettingsComponent
  ],
  imports: [
    CommonModule,
    KanbanRoutingModule
  ]
})
export class KanbanModule { }
