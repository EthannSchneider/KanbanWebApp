import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SettingsComponent } from './settings/settings.component';
import { BoardComponent } from './board/board.component';
import { BacklogComponent } from './backlog/backlog.component';

const routes: Routes = [
  { path: ':name', redirectTo: ':name/board', pathMatch: 'full' },
  { path: ':name/board', component: BoardComponent },
  { path: ':name/backlog', component: BacklogComponent },
  { path: ':name/settings', component: SettingsComponent },
  { path: '**', redirectTo: '/' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KanbanRoutingModule { }
