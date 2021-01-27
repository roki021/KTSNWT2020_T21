import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { SubtypeService } from '../../services/subtype.service';

import { AddSubtypeComponent } from './add-subtype.component';

describe('AddSubtypeComponent', () => {
  let component: AddSubtypeComponent;
  let fixture: ComponentFixture<AddSubtypeComponent>;
  let typeService: SubtypeService;
  let activeModal: NgbActiveModal;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddSubtypeComponent],
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {

    TestBed.configureTestingModule({
      declarations: [AddSubtypeComponent],
      providers: [{ provide: SubtypeService },
        NgbModal, NgbActiveModal]
    });

    fixture = TestBed.createComponent(AddSubtypeComponent);
    component = fixture.componentInstance;
    typeService = TestBed.inject(SubtypeService);
    activeModal = TestBed.inject(NgbActiveModal);

    component.offerTypeName = 'park';
    component.subtype = { id: null, name: 'akva', offerNumber: 1, offerTypeName: 'park' };
    component.refresh = () => { };

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('create new type', () => {
    let newType = {
      id: 1,
      name: 'akva',
      offerNumber: 1,
      offerTypeName: 'park'
    };

    let toAddSubtype = {
      id: null,
      name: 'akva',
      offerNumber: 1,
      offerTypeName: 'park'
    };

    spyOn(typeService, "create").and.returnValue(of({ body: newType }));
    spyOn(activeModal, 'close').and.callFake(() => {});

    component.add();
    expect(typeService.create).toHaveBeenCalledWith(toAddSubtype);
    expect(component.badRequest).toBe(false);
    expect(activeModal.close).toHaveBeenCalled();

  });

  it('should error', () => {
    const error = new Observable((observer) => {
      observer.error({ status: 400 });

    });
    spyOn(typeService, "create").and.returnValue(error);

    component.add();
    expect(component.badRequest).toBe(true);
  });

});
