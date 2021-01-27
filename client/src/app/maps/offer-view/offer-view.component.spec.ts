import { DebugElement, SimpleChange } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
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

  const authServiceMock = {
    getUserId: jasmine.createSpy('getUserId')
      .and.returnValue(1001),
    getRole: jasmine.createSpy('getRole')
      .and.returnValue('GUEST_ROLE'),
    isLoggedIn: jasmine.createSpy('isLoggedIn')
      .and.returnValue(true)
  };

  beforeEach(async(() => {
    authServiceMock.isLoggedIn.and.returnValue(true);
    const subscriptionServiceMock = {
      getUserSubscriptions: jasmine.createSpy('getUserSubscriptions')
        .and.returnValue(of([{ culturalOfferId: 1 }, { culturalOfferId: 2 }, { culturalOfferId: 3 }])),
      unsubscribe: jasmine.createSpy('unsubscribe')
        .and.returnValue(of({})),
      subscribe: jasmine.createSpy('subscribe')
        .and.returnValue(of({}))
    };

    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    const gradesServiceMock = {
      getAvgGrade: jasmine.createSpy('getAvgGrade')
        .and.returnValue(of(5))
    };

    TestBed.configureTestingModule({
      declarations: [OfferViewComponent],
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
      ],
      imports: [NgbNavModule]
    });

    fixture = TestBed.createComponent(OfferViewComponent);
    component = fixture.componentInstance;
    subsService = TestBed.inject(SubscriptionService);
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    toastService = TestBed.inject(ToastService);
    gradesService = TestBed.inject(GradesService);
  }));

  it('shows cultural offer with avg grade and title when logged in as guest and subscribed for opened offer', async(() => {
    authServiceMock.getRole.and.returnValue('ROLE_GUEST');
    component.ngOnInit();

    component.selectedOffer = {
      id: 1,
      address: 'Adresa 1',
      description: 'Opis',
      imageUrls: [],
      latitude: 50,
      longitude: 50,
      offerType: 'Tip',
      subTypeName: 'Podtip',
      title: 'Ponuda 1'
    };

    component.ngOnChanges({
      selectedOffer: {
        firstChange: false,
        currentValue: null,
        previousValue: null,
        isFirstChange: () => false
      }
    });

    expect(authService.getRole).toHaveBeenCalled();
    expect(authService.isLoggedIn).toHaveBeenCalled();
    expect(subsService.getUserSubscriptions).toHaveBeenCalled();
    expect(gradesService.getAvgGrade).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.overallGrade).toBe(5);
        expect(component.isSubed).toBe(true);
        fixture.detectChanges();
        const offerTitle: DebugElement =
          fixture.debugElement.query(By.css('#offer-title'));
        expect(offerTitle).toBeDefined();
        expect(offerTitle.nativeElement.textContent.trim()).toBe('Ponuda 1');
        expect(fixture.debugElement.query(By.css('ngb-rating'))).toBeDefined();
        const subButton: DebugElement =
          fixture.debugElement.query(By.css('#button-subscribe'));
        expect(subButton).toBeDefined();
        expect(subButton.nativeElement.textContent.trim()).toBe('Unsubscribe');
      });
  }));

  it('shows cultural offer with avg grade and title when logged in as guest and not subscribed for opened offer', async(() => {
    authServiceMock.getRole.and.returnValue('ROLE_GUEST');
    component.ngOnInit();

    component.selectedOffer = {
      id: 4,
      address: 'Adresa 1',
      description: 'Opis',
      imageUrls: [],
      latitude: 50,
      longitude: 50,
      offerType: 'Tip',
      subTypeName: 'Podtip',
      title: 'Ponuda 1'
    };

    component.ngOnChanges({
      selectedOffer: {
        firstChange: false,
        currentValue: null,
        previousValue: null,
        isFirstChange: () => false
      }
    });

    expect(authService.getRole).toHaveBeenCalled();
    expect(authService.isLoggedIn).toHaveBeenCalled();
    expect(subsService.getUserSubscriptions).toHaveBeenCalled();
    expect(gradesService.getAvgGrade).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.overallGrade).toBe(5);
        expect(component.isSubed).toBe(false);
        fixture.detectChanges();
        const offerTitle: DebugElement =
          fixture.debugElement.query(By.css('#offer-title'));
        expect(offerTitle).toBeDefined();
        expect(offerTitle.nativeElement.textContent.trim()).toBe('Ponuda 1');
        expect(fixture.debugElement.query(By.css('ngb-rating'))).toBeDefined();
        const subButton: DebugElement =
          fixture.debugElement.query(By.css('#button-subscribe'));
        expect(subButton).toBeDefined();
        expect(subButton.nativeElement.textContent.trim()).toBe('Subscribe');
      });
  }));

  it('shows cultural offer with avg grade and title when logged in as admin', async(() => {
    authServiceMock.getRole.and.returnValue('ROLE_ADMIN');
    component.ngOnInit();

    component.selectedOffer = {
      id: 4,
      address: 'Adresa 1',
      description: 'Opis',
      imageUrls: [],
      latitude: 50,
      longitude: 50,
      offerType: 'Tip',
      subTypeName: 'Podtip',
      title: 'Ponuda 1'
    };

    component.ngOnChanges({
      selectedOffer: {
        firstChange: false,
        currentValue: null,
        previousValue: null,
        isFirstChange: () => false
      }
    });

    expect(authService.getRole).toHaveBeenCalled();
    expect(gradesService.getAvgGrade).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.overallGrade).toBe(5);
        expect(component.isSubed).toBeUndefined();
        fixture.detectChanges();
        const offerTitle: DebugElement =
          fixture.debugElement.query(By.css('#offer-title'));
        expect(offerTitle).toBeDefined();
        expect(offerTitle.nativeElement.textContent.trim()).toBe('Ponuda 1');
        expect(fixture.debugElement.query(By.css('ngb-rating'))).toBeDefined();
        const subButton: DebugElement =
          fixture.debugElement.query(By.css('#button-subscribe'));
        expect(subButton).toBeDefined();
        expect(subButton.nativeElement.textContent.trim()).toBe('Subscribe');
        expect(subButton.nativeElement.disabled).toBeTrue();
      });
  }));

  it('shows cultural offer with avg grade and title when user is not logged in', async(() => {
    authServiceMock.getRole.and.returnValue(null);
    component.ngOnInit();

    component.selectedOffer = {
      id: 4,
      address: 'Adresa 1',
      description: 'Opis',
      imageUrls: [],
      latitude: 50,
      longitude: 50,
      offerType: 'Tip',
      subTypeName: 'Podtip',
      title: 'Ponuda 1'
    };

    component.ngOnChanges({
      selectedOffer: {
        firstChange: false,
        currentValue: null,
        previousValue: null,
        isFirstChange: () => false
      }
    });

    expect(authService.getRole).toHaveBeenCalled();
    expect(gradesService.getAvgGrade).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.overallGrade).toBe(5);
        expect(component.isSubed).toBeFalse();
        fixture.detectChanges();
        const offerTitle: DebugElement =
          fixture.debugElement.query(By.css('#offer-title'));
        expect(offerTitle).toBeDefined();
        expect(offerTitle.nativeElement.textContent.trim()).toBe('Ponuda 1');
        expect(fixture.debugElement.query(By.css('ngb-rating'))).toBeDefined();
        const subButton: DebugElement =
          fixture.debugElement.query(By.css('#button-subscribe'));
        expect(subButton).toBeDefined();
        expect(subButton.nativeElement.textContent.trim()).toBe('Subscribe');
      });
  }));

  it('redirect unlogged user to login page when subscribe button is pressed', async(() => {
    authServiceMock.getRole.and.returnValue(null);
    authServiceMock.isLoggedIn.and.returnValue(false);
    component.ngOnInit();

    component.selectedOffer = {
      id: 4,
      address: 'Adresa 1',
      description: 'Opis',
      imageUrls: [],
      latitude: 50,
      longitude: 50,
      offerType: 'Tip',
      subTypeName: 'Podtip',
      title: 'Ponuda 1'
    };

    component.ngOnChanges({
      selectedOffer: {
        firstChange: false,
        currentValue: null,
        previousValue: null,
        isFirstChange: () => false
      }
    });

    expect(authService.getRole).toHaveBeenCalled();
    expect(gradesService.getAvgGrade).toHaveBeenCalled();

    component.toggleSubscription();
    expect(router.navigate).toHaveBeenCalledWith(['login']);
  }));

  it('subscribe logged guest on selected offer', async(() => {
    authServiceMock.getRole.and.returnValue('ROLE_GUEST');
    component.ngOnInit();

    component.selectedOffer = {
      id: 4,
      address: 'Adresa 1',
      description: 'Opis',
      imageUrls: [],
      latitude: 50,
      longitude: 50,
      offerType: 'Tip',
      subTypeName: 'Podtip',
      title: 'Ponuda 1'
    };

    component.ngOnChanges({
      selectedOffer: {
        firstChange: false,
        currentValue: null,
        previousValue: null,
        isFirstChange: () => false
      }
    });

    expect(authService.getRole).toHaveBeenCalled();
    expect(authService.isLoggedIn).toHaveBeenCalled();
    expect(subsService.getUserSubscriptions).toHaveBeenCalled();
    expect(gradesService.getAvgGrade).toHaveBeenCalled();

    component.toggleSubscription();
    expect(subsService.subscribe).toHaveBeenCalledWith({
      culturalOfferId: 4,
      guestId: 1001,
      culturalOfferTitle: 'Ponuda 1'
    });

    fixture.whenStable()
      .then(() => {
        expect(component.isSubed).toBeTrue();
        fixture.detectChanges();
        const subButton: DebugElement =
          fixture.debugElement.query(By.css('#button-subscribe'));
        expect(subButton).toBeDefined();
        expect(subButton.nativeElement.textContent.trim()).toBe('Unsubscribe');
      });
  }));

  it('unsubscribe logged guest on selected offer', async(() => {
    authServiceMock.getRole.and.returnValue('ROLE_GUEST');
    component.ngOnInit();

    component.selectedOffer = {
      id: 2,
      address: 'Adresa 1',
      description: 'Opis',
      imageUrls: [],
      latitude: 50,
      longitude: 50,
      offerType: 'Tip',
      subTypeName: 'Podtip',
      title: 'Ponuda 2'
    };

    component.ngOnChanges({
      selectedOffer: {
        firstChange: false,
        currentValue: null,
        previousValue: null,
        isFirstChange: () => false
      }
    });

    expect(authService.getRole).toHaveBeenCalled();
    expect(authService.isLoggedIn).toHaveBeenCalled();
    expect(subsService.getUserSubscriptions).toHaveBeenCalled();
    expect(gradesService.getAvgGrade).toHaveBeenCalled();

    component.toggleSubscription();
    expect(subsService.unsubscribe).toHaveBeenCalledWith({
      culturalOfferId: 2,
      guestId: 1001,
      culturalOfferTitle: 'Ponuda 2'
    });

    fixture.whenStable()
      .then(() => {
        expect(component.isSubed).toBeFalse();
        fixture.detectChanges();
        const subButton: DebugElement =
          fixture.debugElement.query(By.css('#button-subscribe'));
        expect(subButton).toBeDefined();
        expect(subButton.nativeElement.textContent.trim()).toBe('Subscribe');
      });
  }));
});
