import { TestBed } from '@angular/core/testing';

import { OfferNewsService } from './offer-news.service';

describe('OfferNewsService', () => {
  let service: OfferNewsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OfferNewsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
