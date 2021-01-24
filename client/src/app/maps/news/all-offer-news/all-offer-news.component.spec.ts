import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllOfferNewsComponent } from './all-offer-news.component';

describe('AllOfferNewsComponent', () => {
  let component: AllOfferNewsComponent;
  let fixture: ComponentFixture<AllOfferNewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllOfferNewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllOfferNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
