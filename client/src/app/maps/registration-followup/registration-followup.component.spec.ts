import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationFollowupComponent } from './registration-followup.component';

describe('RegistrationFollowupComponent', () => {
  let component: RegistrationFollowupComponent;
  let fixture: ComponentFixture<RegistrationFollowupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationFollowupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationFollowupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
