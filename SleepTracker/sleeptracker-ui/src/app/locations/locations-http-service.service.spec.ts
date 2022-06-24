import { TestBed } from '@angular/core/testing';

import { LocationsHttpServiceService } from './locations-http-service.service';

describe('LocationsHttpServiceService', () => {
  let service: LocationsHttpServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocationsHttpServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
