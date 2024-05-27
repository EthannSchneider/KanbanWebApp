import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoardBacklogComponent } from './board-backlog.component';

@NgModule({
  declarations: [
    BoardBacklogComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    BoardBacklogComponent
  ]
})
export class BoardBacklogModule { }
