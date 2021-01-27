import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Router, ActivatedRoute, convertToParamMap } from '@angular/router';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { of } from 'rxjs';
import { Subtype } from '../../model/subtype';
import { OfferTypeService } from '../../services/offer-type.service';
import { SubtypeService } from '../../services/subtype.service';
import { AddSubtypeComponent } from '../add-subtype/add-subtype.component';
import { UpdateSubtypeComponent } from '../update-subtype/update-subtype.component';

import { SubtypeListComponent } from './subtype-list.component';

export class MockAddNgbModalRef {
  componentInstance = {
    refresh: undefined,
    offerTypeName: undefined
  }
}

export class MockUpdateNgbModalRef {
  componentInstance = {
    refresh: undefined,
    subtype: undefined
  }
}

describe('SubtypeListComponent', () => {
  let component: SubtypeListComponent;
  let fixture: ComponentFixture<SubtypeListComponent>;
  let typeService: OfferTypeService;
  let subtypeService: SubtypeService;
  let route: ActivatedRoute;
  let modalService: NgbModal;
  let mockAddModalRef: MockAddNgbModalRef = new MockAddNgbModalRef();
  let mockUpdateModalRef: MockUpdateNgbModalRef = new MockUpdateNgbModalRef();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SubtypeListComponent],
      imports: [
        NgbModule
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    let subTypeServiceMock = {
      getPage: jasmine.createSpy('getPage')
        .and.returnValue(of({ body: [], headers: { get: (param) => 2 } })),
      delete: jasmine.createSpy('delete')
        .and.returnValue(of()),
      subscribe: jasmine.createSpy('subscribe')
    };

    let typeServiceMock = {
      getById: jasmine.createSpy('getById')
        .and.returnValue(of({
          body: {
            id: 1,
            name: 'park',
            subtypesNumber: 2,
            subtypes: ['muzej', 'pozoriste']
          }
        })),
      subscribe: jasmine.createSpy('subscribe')
    };

    TestBed.configureTestingModule({
      declarations: [SubtypeListComponent],
      providers: [{ provide: OfferTypeService, useValue: typeServiceMock },
      { provide: SubtypeService, useValue: subTypeServiceMock },
      {
        provide: ActivatedRoute,
        useValue: {
          snapshot: {
            paramMap: convertToParamMap({
              id: '1'
            })
          }
        }
      }]
    });
    fixture = TestBed.createComponent(SubtypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    typeService = TestBed.inject(OfferTypeService);
    subtypeService = TestBed.inject(SubtypeService);
    route = TestBed.inject(ActivatedRoute);
    modalService = TestBed.inject(NgbModal);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch the subtype list on init', async(() => {
    component.ngOnInit();
    // should subscribe on RegenerateData
    expect(subtypeService.getPage).toHaveBeenCalled();
    expect(typeService.getById).toHaveBeenCalledWith('1');
    expect(component.subtypeList.length).toBe(0);
    expect(component.validOfferType.name).toBe('park');
    
  }));

  it('should call delete of choosen subtype', () => {
    component.delete(1);

    expect(subtypeService.delete).toHaveBeenCalledWith(1);
  });

  it('should call change page for subtype table', () => {
    component.changePage(2);

    expect(subtypeService.getPage).toHaveBeenCalledWith(1, 2, '1');
  });

  it('should open pop-up for adding new subtypes', () => {
    spyOn(modalService, 'open').and.returnValue(mockAddModalRef as any);
    component.addNew();
    expect(modalService.open).toHaveBeenCalledWith(AddSubtypeComponent, { ariaLabelledBy: 'add-subtype', size: 'lg', scrollable: true });
    expect(mockAddModalRef.componentInstance.offerTypeName).toEqual('park');
  });

  it('should open pop-up for updating selected subtypes', () => {
    const mockType: Subtype =
    {
      id: 1,
      name: 'akva',
      offerNumber: 2,
      offerTypeName: 'park'
    };
    spyOn(modalService, 'open').and.returnValue(mockUpdateModalRef as any);
    component.update(mockType);
    expect(modalService.open).toHaveBeenCalledWith(UpdateSubtypeComponent, { ariaLabelledBy: 'update-subtype', size: 'lg', scrollable: true });
    expect(mockUpdateModalRef.componentInstance.subtype.name).toEqual('akva');
  });
});
