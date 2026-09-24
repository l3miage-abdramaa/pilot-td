import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { App } from './app';

describe('App', () => {
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('affiche le titre PILOT-TD', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();

    httpMock.match('/actuator/health');

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('PILOT-TD');
  });

  it('affiche serveur opérationnel quand le backend répond UP', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();

    const requete = httpMock.expectOne('/actuator/health');
    requete.flush({ status: 'UP' });
    await fixture.whenStable();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('p')?.textContent).toContain('Serveur opérationnel');
  });


  it('affiche serveur injoignable quand le backend ne répond pas', async () => {
  const fixture = TestBed.createComponent(App);
  await fixture.whenStable();

  const requete = httpMock.expectOne('/actuator/health');
  requete.error(new ProgressEvent('error'));
  await fixture.whenStable();

  const compiled = fixture.nativeElement as HTMLElement;
  expect(compiled.querySelector('p')?.textContent).toContain('Serveur injoignable');

});
});