import { TestBed } from '@angular/core/testing';

import { InmemoryDataService } from './inmemory.data';

describe('InmemoryData', () => {
  let service: InmemoryDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InmemoryDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
