import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferNewsViewComponent } from './offer-news-view.component';

describe('OfferNewsViewComponent', () => {
  let component: OfferNewsViewComponent;
  let fixture: ComponentFixture<OfferNewsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferNewsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferNewsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
