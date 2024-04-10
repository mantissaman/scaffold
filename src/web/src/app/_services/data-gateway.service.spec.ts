import { TestBed } from '@angular/core/testing';

import { DataGatewayService } from './data-gateway.service';

describe('DataGatewayService', () => {
  let service: DataGatewayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataGatewayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
