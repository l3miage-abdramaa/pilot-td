import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { CreerProjet } from './creer-projet';

describe('CreerProjet', () => {
  let httpMock: HttpTestingController;

  const PROJET_VALIDE = {
    intitule: 'Construction ecole primaire',
    description: 'Six salles de classe',
    budget: 85000000,
    dateDebut: '2027-01-15',
    dateFin: '2027-10-31',
    provinceId: 'p1',
    secteurId: 's1',
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreerProjet],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  /** Cree le composant et repond aux deux chargements initiaux. */
  async function ouvrirEcran() {
    const fixture = TestBed.createComponent(CreerProjet);
    await fixture.whenStable();

    httpMock.expectOne('/api/provinces').flush([{ id: 'p1', nom: 'Batha' }]);
    httpMock.expectOne('/api/secteurs').flush([{ id: 's1', nom: 'Education' }]);
    await fixture.whenStable();

    return fixture;
  }

  function soumettre(fixture: Awaited<ReturnType<typeof ouvrirEcran>>) {
    const form = (fixture.nativeElement as HTMLElement).querySelector('form')!;
    form.dispatchEvent(new Event('submit'));
  }

  it('charge les provinces et les secteurs a l\'ouverture', async () => {
    const fixture = await ouvrirEcran();

    const texte = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(texte).toContain('Batha');
    expect(texte).toContain('Education');
  });

  it("n'envoie rien quand le formulaire est invalide", async () => {
    const fixture = await ouvrirEcran();

    soumettre(fixture);
    await fixture.whenStable();

    httpMock.expectNone('/api/projets');

    const texte = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(texte).toContain("L'intitulé est obligatoire");
  });

  it('affiche le message du serveur quand la creation echoue', async () => {
    const fixture = await ouvrirEcran();

    fixture.componentInstance.formulaire.setValue(PROJET_VALIDE);
    await fixture.whenStable();

    soumettre(fixture);
    await fixture.whenStable();

    httpMock.expectOne('/api/projets').flush(
      { detail: 'RG-VO-04 : la date de fin ne peut pas preceder la date de debut' },
      { status: 422, statusText: 'Unprocessable Entity' },
    );
    await fixture.whenStable();

    const texte = (fixture.nativeElement as HTMLElement).textContent ?? '';
    expect(texte).toContain('RG-VO-04');
  });
});
