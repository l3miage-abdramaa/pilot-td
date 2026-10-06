import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreerProjet } from './creer-projet';

describe('CreerProjet', () => {
  let component: CreerProjet;
  let fixture: ComponentFixture<CreerProjet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreerProjet],
    }).compileComponents();

    fixture = TestBed.createComponent(CreerProjet);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
