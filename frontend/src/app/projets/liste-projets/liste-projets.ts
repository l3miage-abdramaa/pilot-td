import { Component, OnInit, inject, signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProjetApi } from '../projet';
import { EtatProjet, PageReponse, ProjetResume } from '../projet.model';

type EtatListe =
  | { statut: 'chargement' }
  | { statut: 'succes'; page: PageReponse<ProjetResume> }
  | { statut: 'erreur'; message: string };

@Component({
  selector: 'app-liste-projets',
  imports: [DecimalPipe,RouterLink],
  templateUrl: './liste-projets.html',
  styleUrl: './liste-projets.scss',
})
export class ListeProjets implements OnInit {

  private readonly api = inject(ProjetApi);

  protected readonly etat = signal<EtatListe>({ statut: 'chargement' });

  ngOnInit(): void {
    this.charger();
  }

  protected libelleEtat(etat: EtatProjet): string {
    switch (etat) {
      case 'EN_PREPARATION': return 'En préparation';
      case 'EN_COURS': return 'En cours';
      case 'CLOTURE': return 'Clôturé';
    }
  }

  private charger(): void {
    this.etat.set({ statut: 'chargement' });

    this.api.lister(0, 20).subscribe({
      next: (page) => this.etat.set({ statut: 'succes', page }),
      error: () => this.etat.set({
        statut: 'erreur',
        message: 'Impossible de charger les projets. Vérifiez que le serveur est démarré.'
      }),
    });
  }
}