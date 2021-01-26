import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Subtype } from '../model/subtype';

import { GenTableComponent } from './gen-table.component';

describe('GenTableComponent', () => {
  let component: GenTableComponent<Subtype>;
  let fixture: ComponentFixture<GenTableComponent<Subtype>>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent<GenTableComponent<Subtype>>(GenTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
