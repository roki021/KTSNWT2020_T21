import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of, Observable } from 'rxjs';
import { ProfileService } from '../services/profile.service';

import { ChangePasswordComponent } from './change-password.component';

describe('ChangePasswordComponent', () => {
  let component: ChangePasswordComponent;
  let fixture: ComponentFixture<ChangePasswordComponent>;
  let service: ProfileService;
  let activeModal: NgbActiveModal;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangePasswordComponent ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChangePasswordComponent],
      providers: [{ provide: ProfileService },
        NgbModal, NgbActiveModal]
    });
    fixture = TestBed.createComponent(ChangePasswordComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ProfileService);
    activeModal = TestBed.inject(NgbActiveModal);
    component.password = { oldPassword: '12345', newPassword: '123', repetedPassword: '123' };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('update password', () => {
    const password = { oldPassword: '12345', newPassword: '123', repetedPassword: '123' };

    spyOn(service, "changePassword").and.returnValue(of(true));
    spyOn(activeModal, 'close').and.callFake(() => {});

    component.change();
    expect(service.changePassword).toHaveBeenCalledWith(password, component.guestId);
    expect(component.badRequest).toBe(false);
    expect(activeModal.close).toHaveBeenCalled();

  });

  it('should error', () => {
    const error = new Observable((observer) => {
      observer.error({status: 400});
      
    });
    spyOn(service, "changePassword").and.returnValue(error);
    
    component.change();
    expect(component.badRequest).toBe(true);
  });
});
