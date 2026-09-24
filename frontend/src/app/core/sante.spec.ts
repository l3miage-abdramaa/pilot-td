import { TestBed } from '@angular/core/testing';
import { Sante } from './sante';

describe('Sante', () => {
  let service: Sante;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Sante);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
