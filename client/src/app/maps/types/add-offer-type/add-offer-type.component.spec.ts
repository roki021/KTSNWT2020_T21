import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { OfferTypeService } from '../../services/offer-type.service';

import { AddOfferTypeComponent } from './add-offer-type.component';

describe('AddOfferTypeComponent', () => {
  let component: AddOfferTypeComponent;
  let fixture: ComponentFixture<AddOfferTypeComponent>;
  let typeService: OfferTypeService;
  let activeModal: NgbActiveModal;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddOfferTypeComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    let typeServiceMock = {
      create: jasmine.createSpy('create')
        .and.returnValue(of({
          body: {
            id: 1,
            name: 'park',
            subtypesNumber: 2,
            subtypes: ['muzej', 'pozoriste']
          }
        }))
    };
    let mockActiveModal = {
      close: jasmine.createSpy('close').and.callFake
    }
    TestBed.configureTestingModule({
      declarations: [AddOfferTypeComponent],
      providers: [{ provide: OfferTypeService, useValue: typeServiceMock },
        NgbModal, NgbActiveModal]
    });
    fixture = TestBed.createComponent(AddOfferTypeComponent);
    component = fixture.componentInstance;
    typeService = TestBed.inject(OfferTypeService);
    activeModal = TestBed.inject(NgbActiveModal);
    component.offerType = {
      id: 1,
      name: 'park',
      subtypesNumber: 2,
      subtypes: ['muzej', 'pozoriste']
    }
    component.refresh = () => { };
    component.subtypes = ['muzej', 'pozoriste'];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add new subtype name to list of subtypes of new type', () => {
    component.subtype = 'new subtype';
    component.addSubtype();
    expect(component.subtype).toBe('');
    expect(component.subtypes.length).toBe(3);
  });

  it('remove subtype from list', () => {
    component.remove(1);
    expect(component.subtypes.length).toBe(1);
  });

  it('create new type', () => {
    let newType = {
      id: 1,
      name: 'park',
      subtypesNumber: 2,
      subtypes: ['muzej', 'pozoriste']
    }

    component.add();
    expect(typeService.create).toHaveBeenCalledWith(newType);
    //expect(activeModal.close).toHaveBeenCalled();

  });
});
