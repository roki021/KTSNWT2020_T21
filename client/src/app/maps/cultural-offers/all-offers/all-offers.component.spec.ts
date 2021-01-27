import { NgModule } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { CulturalOfferService } from '../../services/cultural-offer.service';
import {RouterTestingModule} from '@angular/router/testing';

import { AllOffersComponent } from './all-offers.component';

describe('AllOffersComponent', () => {
  let component: AllOffersComponent;
  let fixture: ComponentFixture<AllOffersComponent>;
  let service: CulturalOfferService;
  let router: Router;
  let modalService: NgbModal;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AllOffersComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    let offerServiceMock = {
      getAll: jasmine.createSpy('getAll')
        .and.returnValue(of([{}, {}])),
      getPage2: jasmine.createSpy('getPage2')
        .and.returnValue(of([{}])),
      delete: jasmine.createSpy('delete')
        .and.returnValue(of())
    };

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [AllOffersComponent],
      providers: [{ provide: CulturalOfferService, useValue: offerServiceMock },
        { provide: Router, useValue: routerMock}, NgbModal]
    });
    fixture = TestBed.createComponent(AllOffersComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(CulturalOfferService);
    router = TestBed.inject(Router);
    modalService = TestBed.inject(NgbModal);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get some offers', () => {
    component.pageSize = 2;
    component.changePage(1);
    expect(service.getAll).toHaveBeenCalled();
    expect(service.getPage2).toHaveBeenCalledWith(0, 2);
  });

  it('should delete offer', () => {
    component.removeOffer(1);
    expect(service.delete).toHaveBeenCalledWith(1);
  });
});