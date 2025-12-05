import { TestBed } from '@angular/core/testing';
import { CanDeactivateFn } from '@angular/router';
import { provideZonelessChangeDetection } from '@angular/core';

import { confirmLeaveGuard } from './confirm-leave-guard';

describe('confirmLeaveGuard', () => {
  const executeGuard: CanDeactivateFn<any> = (...guardParameters) => 
      TestBed.runInInjectionContext(() => confirmLeaveGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideZonelessChangeDetection()]
    });
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
