import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { OfferNewsService } from '../services/offer-news.service';

import { OfferNewsViewComponent } from './offer-news-view.component';

describe('OfferNewsViewComponent', () => {
  let component: OfferNewsViewComponent;
  let fixture: ComponentFixture<OfferNewsViewComponent>;
  let offerNewsService: OfferNewsService;

  const offerNewsServiceMock = {
    getAll: jasmine.createSpy('getAll')
      .and.returnValue(of([{}, {}, {}, {}])),
    getPage: jasmine.createSpy('getPage')
      .and.returnValue(of([{}, {}, {}]))
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [OfferNewsViewComponent],
      providers: [
        {
          provide: OfferNewsService, useValue: offerNewsServiceMock
        }
      ]
    });

    fixture = TestBed.createComponent(OfferNewsViewComponent);
    component = fixture.componentInstance;
    offerNewsService = TestBed.inject(OfferNewsService);
    component.offer = {
      id: 1,
      address: '',
      description: '',
      imageUrls: [],
      offerType: '',
      subTypeName: '',
      title: '',
      latitude: 0.1,
      longitude: 0.1
    };
  }));

  it('should create offer news view with 3 offer news on first page', async(() => {
    component.ngOnInit();

    expect(offerNewsService.getAll).toHaveBeenCalled();
    expect(offerNewsService.getPage).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.news.length).toBe(3);
        fixture.detectChanges();
      });
  }));

  it('should create offer news view with 1 offer news on second page', async(() => {
    component.ngOnInit();

    expect(offerNewsService.getAll).toHaveBeenCalled();
    expect(offerNewsService.getPage).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.news.length).toBe(3);
        fixture.detectChanges();

        component.changePage(2);
        expect(offerNewsService.getAll).toHaveBeenCalled();
        expect(offerNewsService.getPage).toHaveBeenCalled();
        fixture.detectChanges();

        fixture.whenStable()
          .then(() => {
            expect(component.news.length).toBe(3);
            fixture.detectChanges();
          });
      });
  }));

  it('should open detail offer news view', async(() => {
    component.ngOnInit();

    expect(offerNewsService.getAll).toHaveBeenCalled();
    expect(offerNewsService.getPage).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.news.length).toBe(3);
        component.open('title', 'desc', [])

        expect(component.allOffers).toBe(false);
        fixture.detectChanges();
        let backBtn: DebugElement =
          fixture.debugElement.query(By.css('#back-btn'));

        expect(backBtn).toBeDefined();
        expect(backBtn.nativeElement.textContent.trim()).toBe('Back');
      });
  }));
});
