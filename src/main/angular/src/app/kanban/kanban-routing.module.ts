import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KanbanComponent } from './kanban.component';

const routes: Routes = [
  { path: ':name', component: KanbanComponent },
  { path: '**', redirectTo: '/' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KanbanRoutingModule { }
