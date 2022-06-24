import { TestBed } from '@angular/core/testing';

import { SleepConditionHttpService } from './sleep-condition-http.service';

describe('SleepConditionHttpService', () => {
  let service: SleepConditionHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SleepConditionHttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
