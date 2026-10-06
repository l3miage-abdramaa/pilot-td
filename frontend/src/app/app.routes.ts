import { Routes } from '@angular/router';
import { ListeProjets } from './projets/liste-projets/liste-projets';

export const routes: Routes = [
  { path: '', redirectTo: 'projets', pathMatch: 'full' },
  { path: 'projets', component: ListeProjets}
];
