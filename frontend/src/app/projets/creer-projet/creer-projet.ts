import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProjetApi } from '../projet';
import { ReferenceResume } from '../projet.model';

@Component({
  selector: 'app-creer-projet',
  imports: [ReactiveFormsModule],
  templateUrl: './creer-projet.html',
  styleUrl: './creer-projet.scss',
})
export class CreerProjet implements OnInit {

  private readonly api = inject(ProjetApi);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  protected readonly provinces = signal<ReferenceResume[]>([]);
  protected readonly secteurs = signal<ReferenceResume[]>([]);
  protected readonly envoiEnCours = signal(false);
  protected readonly erreur = signal<string | null>(null);

  protected readonly formulaire = this.fb.nonNullable.group({
    intitule: ['', [Validators.required, Validators.maxLength(255)]],
    description: [''],
    budget: [0, [Validators.required, Validators.min(0)]],
    dateDebut: ['', Validators.required],
    dateFin: ['', Validators.required],
    provinceId: ['', Validators.required],
    secteurId: ['', Validators.required],
  });

  ngOnInit(): void {
    this.api.provinces().subscribe((p) => this.provinces.set(p));
    this.api.secteurs().subscribe((s) => this.secteurs.set(s));
  }

  protected enregistrer(): void {
    if (this.formulaire.invalid || this.envoiEnCours()) {
      this.formulaire.markAllAsTouched();
      return;
    }

    this.envoiEnCours.set(true);
    this.erreur.set(null);

    this.api.creer(this.formulaire.getRawValue()).subscribe({
      next: () => this.router.navigate(['/projets']),
      error: (e) => {
        this.envoiEnCours.set(false);
        this.erreur.set(e.error?.detail ?? 'La création a échoué.');
      },
    });
  } 


  protected annuler(): void {
    this.router.navigate(['/projets']);
  }
}