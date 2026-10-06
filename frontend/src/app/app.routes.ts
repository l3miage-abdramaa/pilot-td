import { Routes } from '@angular/router';
import { ListeProjets } from './projets/liste-projets/liste-projets';
import { CreerProjet } from './projets/creer-projet/creer-projet';

export const routes: Routes = [
  { path: '', redirectTo: 'projets', pathMatch: 'full' },
  { path: 'projets', component: ListeProjets },
  { path: 'projets/nouveau', component: CreerProjet },
];
