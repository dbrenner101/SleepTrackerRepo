import { TestBed } from '@angular/core/testing';

import { Health.HttpService } from './health.http.service';

describe('Health.HttpService', () => {
  let service: Health.HttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Health.HttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
