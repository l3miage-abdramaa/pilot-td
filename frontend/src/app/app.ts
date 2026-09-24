import { Sante } from './core/sante';
import { Component, signal, inject, OnInit } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-root',
  styleUrl: './app.scss',
  templateUrl: './app.html',
})
export class App implements OnInit {
  protected readonly title = signal('Abdou');

  private readonly sante = inject(Sante);

  protected readonly message = signal('Vérification...');


  ngOnInit(): void {
    this.sante.consulterEtat().subscribe({
      next: (etat) => {
        // ici, etat.status vaut 'UP' quand tout va bien
        this.message.set('Serveur opérationnel.');
      },
      error: () => {
        this.message.set('Serveur injoignable.');
      }
  });
}
}
