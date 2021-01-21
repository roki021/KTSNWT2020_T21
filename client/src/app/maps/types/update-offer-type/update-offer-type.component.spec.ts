import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateOfferTypeComponent } from './update-offer-type.component';

describe('UpdateOfferTypeComponent', () => {
  let component: UpdateOfferTypeComponent;
  let fixture: ComponentFixture<UpdateOfferTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateOfferTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateOfferTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
