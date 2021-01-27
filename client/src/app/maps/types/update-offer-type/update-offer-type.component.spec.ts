import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of, Observable } from 'rxjs';
import { OfferType } from '../../model/offer-type';
import { OfferTypeService } from '../../services/offer-type.service';

import { UpdateOfferTypeComponent } from './update-offer-type.component';

describe('UpdateOfferTypeComponent', () => {
  let component: UpdateOfferTypeComponent;
  let fixture: ComponentFixture<UpdateOfferTypeComponent>;
  let typeService: OfferTypeService;
  let activeModal: NgbActiveModal;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateOfferTypeComponent ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateOfferTypeComponent],
      providers: [{ provide: OfferTypeService },
        NgbModal, NgbActiveModal]
    });
    fixture = TestBed.createComponent(UpdateOfferTypeComponent);
    component = fixture.componentInstance;
    typeService = TestBed.inject(OfferTypeService);
    activeModal = TestBed.inject(NgbActiveModal);
    component.offerType = {
      id: 1,
      name: 'park',
      subtypesNumber: 2,
      subtypes: ['muzej', 'pozoriste']
    };
    component.refresh = () => {};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('update select type', () => {
    let updateType:OfferType = {
      id: 1,
      name: 'park',
      subtypesNumber: 2,
      subtypes: ['muzej', 'pozoriste']
    };

    spyOn(typeService, "update").and.returnValue(of({ body: updateType }));
    spyOn(activeModal, 'close').and.callFake(() => {});

    component.update();
    expect(typeService.update).toHaveBeenCalledWith(updateType, updateType.id);
    expect(component.badRequest).toBe(false);
    expect(activeModal.close).toHaveBeenCalled();

  });

  it('should error', () => {
    const error = new Observable((observer) => {
      observer.error({status: 400});
      
    });
    spyOn(typeService, "update").and.returnValue(error);
    
    component.update();
    expect(component.badRequest).toBe(true);
  });
});
