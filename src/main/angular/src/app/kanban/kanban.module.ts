import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KanbanComponent } from './kanban.component';
import { KanbanRoutingModule } from './kanban-routing.module';



@NgModule({
  declarations: [
    KanbanComponent
  ],
  imports: [
    CommonModule,
    KanbanRoutingModule
  ]
})
export class KanbanModule { }
