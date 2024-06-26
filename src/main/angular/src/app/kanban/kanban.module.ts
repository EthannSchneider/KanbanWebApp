import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KanbanRoutingModule } from './kanban-routing.module';
import { SettingsComponent } from './settings/settings.component';
import { BacklogComponent } from './backlog/backlog.component';
import { BoardComponent } from './board/board.component';
import { BoardBacklogModule } from '../@core/component/board-backlog/board-backlog.module';
import { TaskDetailsComponent } from './task-details/task-details.component';



@NgModule({
  declarations: [
    SettingsComponent,
    BacklogComponent,
    BoardComponent,
    TaskDetailsComponent
  ],
  imports: [
    CommonModule,
    KanbanRoutingModule,
    BoardBacklogModule
  ]
})
export class KanbanModule { }
