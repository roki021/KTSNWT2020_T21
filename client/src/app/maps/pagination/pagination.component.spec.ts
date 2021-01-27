import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { subscribeOn } from 'rxjs/operators';

import { PaginationComponent } from './pagination.component';

describe('PaginationComponent', () => {
  let component: PaginationComponent;
  let fixture: ComponentFixture<PaginationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaginationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaginationComponent]
    });
    fixture = TestBed.createComponent(PaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.pageSize = 2;
    component.totalPages = 3;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load pagination list', () => {
    component.ngOnInit();

    expect(component.pages.length).toBe(3);
    expect(component.collectionSize).toBe(6);
  });

  it('should load pagination list', () => {
    const changes = { totalPages:{ currentValue: 4 }};
    component.ngOnChanges(changes);

    expect(component.pages.length).toBe(4);
    expect(component.collectionSize).toBe(8);
  });

  it('it should change page', () => {
    spyOn(component.pageSelected, 'emit').and.callFake(() => {});
    component.selected(2);
    fixture.detectChanges();
    expect(component.activePage).toBe(2);
    expect(component.pageSelected.emit).toHaveBeenCalled();
  });
});
