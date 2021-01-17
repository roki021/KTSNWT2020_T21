import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubtypeTableComponent } from './subtype-table.component';

describe('SubtypeTableComponent', () => {
  let component: SubtypeTableComponent;
  let fixture: ComponentFixture<SubtypeTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubtypeTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubtypeTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
