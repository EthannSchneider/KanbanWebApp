import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KanbanComponent } from './kanban.component';
import { SettingsComponent } from './settings/settings.component';

const routes: Routes = [
  { path: ':name', component: KanbanComponent },
  { path: ':name/settings', component: SettingsComponent },
  { path: '**', redirectTo: '/' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KanbanRoutingModule { }
