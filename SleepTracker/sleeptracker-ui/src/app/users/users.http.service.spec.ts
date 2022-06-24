import { TestBed } from '@angular/core/testing';

import { Users.HttpService } from './users.http.service';

describe('Users.HttpService', () => {
  let service: Users.HttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Users.HttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
