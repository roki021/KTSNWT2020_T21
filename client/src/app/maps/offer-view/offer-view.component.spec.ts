import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { GradesService } from '../services/grades.service';
import { SubscriptionService } from '../services/subscription.service';
import { ToastService } from '../toasts/toast-service';

import { OfferViewComponent } from './offer-view.component';

describe('OfferViewComponent', () => {
  let component: OfferViewComponent;
  let fixture: ComponentFixture<OfferViewComponent>;
  let subsService: SubscriptionService;
  let authService: AuthService;
  let router: Router;
  let toastService: ToastService;
  let gradesService: GradesService;
  

  beforeEach(async(() => {
    const subscriptionServiceMock = {
      getUserSubscriptions: jasmine.createSpy('getUserSubscriptions')
        .and.returnValue(of([{}, {}, {}])),
      unsubscribe: jasmine.createSpy('unsubscribe')
        .and.returnValue(of({})),
      subscribe: jasmine.createSpy('subscribe')
        .and.returnValue(of({}))
    };

    const authServiceMock = {
      getUserId: jasmine.createSpy('getUserId')
        .and.returnValue(1001),
      getRole: jasmine.createSpy('getRole')
        .and.returnValue(''),
      isLoggedIn: jasmine.createSpy('isLoggedIn')
        .and.returnValue(true)
    };

    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    const gradesServiceMock = {
      getAvgGrade: jasmine.createSpy('getAvgGrade')
        .and.returnValue(of())
    };
    
    TestBed.configureTestingModule({
      declarations: [ OfferViewComponent ],
      providers: [
        {
          provide: SubscriptionService, useValue: subscriptionServiceMock
        },
        {
          provide: AuthService, useValue: authServiceMock
        },
        {
          provide: Router, useValue: routerMock
        },
        {
          provide: GradesService, useValue: gradesServiceMock
        }
      ]
    });

    fixture = TestBed.createComponent(OfferViewComponent);
    component = fixture.componentInstance;
    subsService = TestBed.inject(SubscriptionService);
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    toastService = TestBed.inject(ToastService);
    gradesService = TestBed.inject(GradesService);

    
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
