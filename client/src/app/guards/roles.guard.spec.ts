import { TestBed } from '@angular/core/testing';

import { RolesGuard } from './roles.guard';

describe('LoginGuard', () => {
  let guard: RolesGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RolesGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
