import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from '../services/auth.service';

import { OptionNavbarComponent } from './option-navbar.component';

describe('OptionNavbarComponent', () => {
  let component: OptionNavbarComponent;
  let fixture: ComponentFixture<OptionNavbarComponent>;
  let authService: AuthService;
  let modalService: NgbModal;

  const authServiceMock = {
    getRole: jasmine.createSpy('getRole')
      .and.returnValue(''),
    logout: jasmine.createSpy('logout'),
    getUserId: jasmine.createSpy('getUserId')
      .and.returnValue(1001)
  };

  beforeEach(async(() => {
    const modalServiceMock = {
      open: jasmine.createSpy('open')
        .and.returnValue({componentInstance: { guestId: 1001 }})
    };

    TestBed.configureTestingModule({
      declarations: [ OptionNavbarComponent ],
      providers: [
        {
          provide: AuthService, useValue: authServiceMock
        },
        {
          provide: NgbModal, useValue: modalServiceMock
        }
      ]
    });

    fixture = TestBed.createComponent(OptionNavbarComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    modalService = TestBed.inject(NgbModal);
  }));

  it('should create navbar for unlogged user', async(() => {
    authServiceMock.getRole.and.returnValue(null);
    component.ngOnInit();

    expect(authService.getRole).toHaveBeenCalled();
    fixture.whenStable()
      .then(() => {
        expect(component.authority).toBe(null);
        fixture.detectChanges();
        let links: DebugElement[] = 
          fixture.debugElement.queryAll(By.css('a'));
          expect(links).toBeDefined();
        expect(links.length).toBe(2);
        expect(links[0].nativeElement.textContent.trim()).toBe('Log in');
        expect(links[1].nativeElement.textContent.trim()).toBe('Register');
      });
  }));

  it('should create navbar for logged user as guest', async(() => {
    authServiceMock.getRole.and.returnValue('ROLE_GUEST');
    component.ngOnInit();

    expect(authService.getRole).toHaveBeenCalled();
    fixture.whenStable()
      .then(() => {
        expect(component.authority).toBe('ROLE_GUEST');
        fixture.detectChanges();
        let links: DebugElement[] = 
          fixture.debugElement.queryAll(By.css('a'));
          expect(links).toBeDefined();
        expect(links.length).toBe(2);
        expect(links[0].nativeElement.textContent.trim()).toBe('Profile');
        expect(links[1].nativeElement.textContent.trim()).toBe('Log out');
      });
  }));

  it('should create navbar for logged user as admin', async(() => {
    authServiceMock.getRole.and.returnValue('ROLE_ADMIN');
    component.ngOnInit();

    expect(authService.getRole).toHaveBeenCalled();
    fixture.whenStable()
      .then(() => {
        expect(component.authority).toBe('ROLE_ADMIN');
        fixture.detectChanges();
        let links: DebugElement[] = 
          fixture.debugElement.queryAll(By.css('a'));
          expect(links).toBeDefined();
        expect(links.length).toBe(4);
        expect(links[0].nativeElement.textContent.trim()).toBe('Cultural offers');
        expect(links[1].nativeElement.textContent.trim()).toBe('Offer types');
        expect(links[2].nativeElement.textContent.trim()).toBe('Change Password');
        expect(links[3].nativeElement.textContent.trim()).toBe('Log out');
      });
  }));

  it('should open popup for password change if user is admin', async(() => {
    authServiceMock.getRole.and.returnValue('ROLE_ADMIN');
    component.ngOnInit();

    expect(authService.getRole).toHaveBeenCalled();
    authServiceMock.getRole.and.returnValue(null);
    component.changePassword();
    expect(modalService.open).toHaveBeenCalled();
    expect(authService.getUserId).toHaveBeenCalled();
  }));
});
