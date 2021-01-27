import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of, Observable } from 'rxjs';
import { Subtype } from '../../model/subtype';
import { SubtypeService } from '../../services/subtype.service';

import { UpdateSubtypeComponent } from './update-subtype.component';

describe('UpdateSubtypeComponent', () => {
  let component: UpdateSubtypeComponent;
  let fixture: ComponentFixture<UpdateSubtypeComponent>;
  let typeService: SubtypeService;
  let activeModal: NgbActiveModal;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateSubtypeComponent ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateSubtypeComponent],
      providers: [{ provide: SubtypeService },
        NgbModal, NgbActiveModal]
    });
    fixture = TestBed.createComponent(UpdateSubtypeComponent);
    component = fixture.componentInstance;
    typeService = TestBed.inject(SubtypeService);
    activeModal = TestBed.inject(NgbActiveModal);
    component.subtype = {
      id: 1,
      name: 'akva',
      offerNumber: 2,
      offerTypeName: 'park'
    };
    component.refresh = () => {};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('update select subtype', () => {
    let updateType:Subtype = {
      id: 1,
      name: 'akva',
      offerNumber: 2,
      offerTypeName: 'park'
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
