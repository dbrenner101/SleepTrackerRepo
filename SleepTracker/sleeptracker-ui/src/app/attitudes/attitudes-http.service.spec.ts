import { TestBed } from '@angular/core/testing';

import { AttitudesHttpService } from './attitudes-http.service';

describe('AttitudesHttpService', () => {
  let service: AttitudesHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AttitudesHttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
