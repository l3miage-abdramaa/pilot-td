import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface EtatServeur {
  status: string;
}


@Injectable({ providedIn: 'root' })
export class Sante {

  private readonly http = inject(HttpClient);

  consulterEtat(): Observable<EtatServeur> {
    return this.http.get<EtatServeur>('/actuator/health');
  }
}
