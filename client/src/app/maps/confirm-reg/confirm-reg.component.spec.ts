import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmRegComponent } from './confirm-reg.component';

describe('ConfirmRegComponent', () => {
  let component: ConfirmRegComponent;
  let fixture: ComponentFixture<ConfirmRegComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmRegComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmRegComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
