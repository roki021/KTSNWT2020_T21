import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditOfferNewsComponent } from './edit-offer-news.component';

describe('EditOfferNewsComponent', () => {
  let component: EditOfferNewsComponent;
  let fixture: ComponentFixture<EditOfferNewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditOfferNewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditOfferNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
