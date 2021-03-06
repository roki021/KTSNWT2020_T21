import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { OfferTypeService } from '../../services/offer-type.service';

import { OfferTypeListComponent } from './offer-type-list.component';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AddOfferTypeComponent } from '../add-offer-type/add-offer-type.component';
import { UpdateOfferTypeComponent } from '../update-offer-type/update-offer-type.component';
import { OfferType } from '../../model/offer-type';
import { HttpClientTestingModule } from '@angular/common/http/testing';


export class MockAddNgbModalRef {
  componentInstance = {
    refresh: undefined
  }
}

export class MockUpdateNgbModalRef {
  componentInstance = {
    refresh: undefined,
    offerType: undefined
  }
}

describe('OfferTypeListComponent', () => {
  let component: OfferTypeListComponent;
  let fixture: ComponentFixture<OfferTypeListComponent>;
  let typeService: OfferTypeService;
  let router: Router;
  let route: ActivatedRoute;
  let modalService: NgbModal;
  let mockAddModalRef: MockAddNgbModalRef = new MockAddNgbModalRef();
  let mockUpdateModalRef: MockUpdateNgbModalRef = new MockUpdateNgbModalRef();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [OfferTypeListComponent],
      imports: [
        NgbModule,
        HttpClientTestingModule
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [OfferTypeListComponent],
      providers: [{ provide: OfferTypeService },
      { provide: Router, useValue: routerMock },
      { provide: ActivatedRoute, useValue: 'http://localhost:4200/offer-types' }]
    });

    fixture = TestBed.createComponent(OfferTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    typeService = TestBed.inject(OfferTypeService);
    router = TestBed.inject(Router);
    route = TestBed.inject(ActivatedRoute);
    modalService = TestBed.inject(NgbModal);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch the subtype list on init', async(() => {
    spyOn(typeService, "getPage").and.returnValue(of({ body: [{},{}], headers: { get: (param) => 2 } }));
    component.ngOnInit();
    expect(typeService.getPage).toHaveBeenCalled();

    expect(component.offerTypeList.length).toBe(2);
    
  }));

  it('should open pop-up for adding new types', () => {
    spyOn(modalService, 'open').and.returnValue(mockAddModalRef as any);
    component.addNew();
    expect(modalService.open).toHaveBeenCalledWith(AddOfferTypeComponent, { ariaLabelledBy: 'add-offer-type', size: 'lg', scrollable: true });
  });

  it('should open pop-up for updating selected types', () => {
    const mockType: OfferType = 
    {
      id: 1,
      name: 'park',
      subtypesNumber: 2,
      subtypes: ['muzej', 'pozoriste']            
    };
    spyOn(modalService, 'open').and.returnValue(mockUpdateModalRef as any);
    component.update(mockType);
    expect(modalService.open).toHaveBeenCalledWith(UpdateOfferTypeComponent, { ariaLabelledBy: 'update-offer-type', size: 'lg', scrollable: true });
    expect(mockUpdateModalRef.componentInstance.offerType.name).toEqual('park');
  });

  it('should navigate to subtypes page of choosen type', () => {
    spyOn(typeService, "getPage").and.returnValue(of({ body: [{},{}], headers: { get: (param) => 2 } }));
    component.ngOnInit();
    component.subtypesView(1);
    expect(router.navigate).toHaveBeenCalledWith(
      ['./1/subtypes'], Object({ relativeTo: 'http://localhost:4200/offer-types' }));
  });

  it ('should call delete of choosen type', () => {
    spyOn(typeService, "delete").and.returnValue(of())
    component.delete(1);

    expect(typeService.delete).toHaveBeenCalledWith(1);    
  });

  it('should error', () => {
    const error = new Observable((observer) => {
      observer.error({ status: 400 });

    });
    spyOn(typeService, "delete").and.returnValue(error);

    component.delete(1);
    expect(component.deleteValidation).toBe(true);
  });
  
  it ('should call change page for type table', () => {
    spyOn(typeService, "getPage").and.returnValue(of({ body: [{},{}], headers: { get: (param) => 2 } }));
    component.changePage(2);

    expect(typeService.getPage).toHaveBeenCalledWith(1, 2);    
  });
});
