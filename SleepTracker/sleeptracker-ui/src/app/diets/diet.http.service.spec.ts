import { TestBed } from '@angular/core/testing';

import { Diet.HttpService } from './diet.http.service';

describe('Diet.HttpService', () => {
  let service: Diet.HttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Diet.HttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
