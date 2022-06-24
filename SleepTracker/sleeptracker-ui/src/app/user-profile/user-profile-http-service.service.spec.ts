import { TestBed } from '@angular/core/testing';

import { UserProfileHttpServiceService } from './user-profile-http-service.service';

describe('UserProfileHttpServiceService', () => {
  let service: UserProfileHttpServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserProfileHttpServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
