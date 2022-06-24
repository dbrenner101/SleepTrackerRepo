import { TestBed } from '@angular/core/testing';

import { Habits.HttpService } from './habits.http.service';

describe('Habits.HttpService', () => {
  let service: Habits.HttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Habits.HttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
