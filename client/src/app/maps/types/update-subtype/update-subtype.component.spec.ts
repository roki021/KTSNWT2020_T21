import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateSubtypeComponent } from './update-subtype.component';

describe('UpdateSubtypeComponent', () => {
  let component: UpdateSubtypeComponent;
  let fixture: ComponentFixture<UpdateSubtypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateSubtypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateSubtypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
