import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferTypeTableComponent } from './offer-type-table.component';

describe('OfferTypeTableComponent', () => {
  let component: OfferTypeTableComponent;
  let fixture: ComponentFixture<OfferTypeTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferTypeTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferTypeTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
