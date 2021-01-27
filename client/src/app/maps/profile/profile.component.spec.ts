import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { ProfileService } from '../services/profile.service';
import { SubscriptionService } from '../services/subscription.service';
import { ToastService } from '../toasts/toast-service';

import { ProfileComponent } from './profile.component';
import { Guest } from '../model/guest';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let profileService: ProfileService;
  let modalService: NgbModal;
  let authService: AuthService;
  let subsService: SubscriptionService;
  let toastService: ToastService;

  beforeEach(async(() => {
    const mockUser: Guest = {
      emailAddress: 'perica@mail.com',
      firstName: 'Petar',
      lastName: 'Petrović',
      password: undefined,
      username: 'perica',
      id: 1001
    };

    const subscriptionServiceMock = {
      getUserSubscriptions: jasmine.createSpy('getUserSubscriptions')
        .and.returnValue(of([{}, {}, {}])),
      unsubscribe: jasmine.createSpy('unsubscribe')
        .and.returnValue(of({}))
    };

    const authServiceMock = {
      getUserId: jasmine.createSpy('getUserId')
        .and.returnValue(1001),
      refreshToken: jasmine.createSpy('refreshToken')
        .and.returnValue(of())
    };

    const profileServiceMock = {
      getProfile: jasmine.createSpy('getProfile')
        .and.returnValue(of({ body: mockUser }))
    };

    TestBed.configureTestingModule({
      declarations: [
        ProfileComponent
      ],
      providers: [
        { provide: ProfileService, useValue: profileServiceMock },
        { provide: SubscriptionService, useValue: subscriptionServiceMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: NgbModal }
      ]
    });

    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    profileService = TestBed.inject(ProfileService);
    modalService = TestBed.inject(NgbModal);
    authService = TestBed.inject(AuthService);
    subsService = TestBed.inject(SubscriptionService);
    toastService = TestBed.inject(ToastService);
  }));

  it('should fetch the subscriptions and profile data on init', async(() => {
    component.ngOnInit();
    expect(authService.getUserId).toHaveBeenCalled();
    expect(profileService.getProfile).toHaveBeenCalled();
    expect(subsService.getUserSubscriptions).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.subscriptions.length).toBe(3);
        fixture.detectChanges();
        const elements: DebugElement[] =
          fixture.debugElement.queryAll(By.css('input'));
        expect(elements.length).toBe(4);
        expect(component.guest).toEqual({
          emailAddress: 'perica@mail.com',
          firstName: 'Petar',
          lastName: 'Petrović',
          password: undefined,
          username: 'perica',
          id: 1001
        });
      });
  }));
});
