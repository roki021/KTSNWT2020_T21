import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferTypeListComponent } from './offer-type-list.component';

describe('OfferTypeListComponent', () => {
  let component: OfferTypeListComponent;
  let fixture: ComponentFixture<OfferTypeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferTypeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
