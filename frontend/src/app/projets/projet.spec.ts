import { TestBed } from '@angular/core/testing';
import { ProjetApi } from './projet';

describe('ProjetApi', () => {
  let service: ProjetApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjetApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
