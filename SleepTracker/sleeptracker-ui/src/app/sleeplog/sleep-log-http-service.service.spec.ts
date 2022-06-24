import { TestBed } from '@angular/core/testing';

import { SleepLogHttpServiceService } from './sleep-log-http-service.service';

describe('SleepLogHttpServiceService', () => {
  let service: SleepLogHttpServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SleepLogHttpServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
