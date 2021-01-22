import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOfferTypeComponent } from './add-offer-type.component';

describe('AddOfferTypeComponent', () => {
  let component: AddOfferTypeComponent;
  let fixture: ComponentFixture<AddOfferTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddOfferTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOfferTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
