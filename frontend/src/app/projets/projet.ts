import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageReponse, ProjetResume, ReferenceResume, CreerProjetRequete } from './projet.model';




@Injectable({ providedIn: 'root' })
export class ProjetApi {

  private readonly http = inject(HttpClient);

  lister(page: number, taille: number): Observable<PageReponse<ProjetResume>> {
    return this.http.get<PageReponse<ProjetResume>>('/api/projets', {
      params: { page, taille }
    });
  } 


    provinces(): Observable<ReferenceResume[]> {
    return this.http.get<ReferenceResume[]>('/api/provinces');
  }

  secteurs(): Observable<ReferenceResume[]> {
    return this.http.get<ReferenceResume[]>('/api/secteurs');
  }

  creer(requete: CreerProjetRequete): Observable<void> {
    return this.http.post<void>('/api/projets', requete);
  } 

  

}
