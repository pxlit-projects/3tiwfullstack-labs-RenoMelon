import { TestBed } from '@angular/core/testing';
import { provideZonelessChangeDetection } from '@angular/core';

import { InmemoryDataService } from './inmemory.data';

describe('InmemoryData', () => {
  let service: InmemoryDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideZonelessChangeDetection()]
    });
    service = TestBed.inject(InmemoryDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
