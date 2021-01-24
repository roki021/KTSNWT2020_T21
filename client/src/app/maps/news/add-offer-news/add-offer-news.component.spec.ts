import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOfferNewsComponent } from './add-offer-news.component';

describe('AddOfferNewsComponent', () => {
  let component: AddOfferNewsComponent;
  let fixture: ComponentFixture<AddOfferNewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddOfferNewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOfferNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
