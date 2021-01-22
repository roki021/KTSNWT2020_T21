import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OptionNavbarComponent } from './option-navbar.component';

describe('OptionNavbarComponent', () => {
  let component: OptionNavbarComponent;
  let fixture: ComponentFixture<OptionNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OptionNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OptionNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
