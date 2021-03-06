import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { OfferTypeService } from '../../services/offer-type.service';

import { AddOfferTypeComponent } from './add-offer-type.component';

describe('AddOfferTypeComponent', () => {
  let component: AddOfferTypeComponent;
  let fixture: ComponentFixture<AddOfferTypeComponent>;
  let typeService: OfferTypeService;
  let activeModal: NgbActiveModal;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddOfferTypeComponent],
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddOfferTypeComponent],
      providers: [{ provide: OfferTypeService },
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
    };
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
    };

    spyOn(typeService, "create").and.returnValue(of({ body: newType }));
    spyOn(activeModal, 'close').and.callFake(() => {});

    component.add();
    expect(typeService.create).toHaveBeenCalledWith(newType);
    expect(component.badRequest).toBe(false);
    expect(activeModal.close).toHaveBeenCalled();

  });

  it('should error', () => {
    const error = new Observable((observer) => {
      observer.error({status: 400});
      
    });
    spyOn(typeService, "create").and.returnValue(error);
    
    component.add();
    expect(component.badRequest).toBe(true);
  });
});
