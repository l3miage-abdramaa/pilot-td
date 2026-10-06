import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { ListeProjets } from './liste-projets';

describe('ListeProjets', () => {
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListeProjets],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('affiche les projets recus', async () => {
    const fixture = TestBed.createComponent(ListeProjets);
    await fixture.whenStable();

    httpMock.expectOne((r) => r.url === '/api/projets').flush({
      contenu: [{
        id: '1', code: 'PRJ-2026-001', intitule: 'Ecole primaire',
        province: 'Batha', secteur: 'Education', etat: 'EN_PREPARATION',
        dateDebut: '2027-01-15', dateFin: '2027-10-31', budget: 85000000,
      }],
      page: 0, taille: 20, total: 1,
    });
    await fixture.whenStable();

    const texte = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(texte).toContain('PRJ-2026-001');
    expect(texte).toContain('En préparation');
  });

  
  it('affiche un message quand la liste est vide', async () => {
    const fixture = TestBed.createComponent(ListeProjets);
    await fixture.whenStable();

    httpMock.expectOne((r) => r.url === '/api/projets').flush({
      contenu: [],
      page: 0, taille: 20, total: 0,
    });
    await fixture.whenStable();

    const texte = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(texte).toContain('Aucun projet enregistré pour le moment.');
  });

  it('affiche un message d\'erreur quand le serveur repond en erreur', async () => {
    const fixture = TestBed.createComponent(ListeProjets);
    await fixture.whenStable();

    httpMock.expectOne((r) => r.url === '/api/projets').flush(null, { status: 500, statusText: 'Server Error' });
    await fixture.whenStable();

    const texte = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(texte).toContain('Impossible de charger les projets. Vérifiez que le serveur est démarré.');
  });
});