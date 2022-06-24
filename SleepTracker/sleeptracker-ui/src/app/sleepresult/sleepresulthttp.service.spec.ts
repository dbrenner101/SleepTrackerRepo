import { TestBed } from '@angular/core/testing';

import { SleepresulthttpService } from './sleepresulthttp.service';

describe('SleepresulthttpService', () => {
  let service: SleepresulthttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SleepresulthttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
