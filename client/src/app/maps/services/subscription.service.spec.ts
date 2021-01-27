import { TestBed, fakeAsync, tick, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { SubscriptionService } from './subscription.service';
import { HttpClient } from '@angular/common/http';
import { Subscription } from '../model/subscription';

describe('SubscriptionService', () => {
  let injector: TestBed;
  let subsService: SubscriptionService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  let authService: AuthService;


  beforeEach(() => {

    const authMockService = {
      getUserId: jasmine.createSpy('getUserId')
        .and.returnValue(1001)
    };

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        SubscriptionService,
        { provide: AuthService, useValue: authMockService }
      ]
    });
    injector = getTestBed();
    subsService = TestBed.inject(SubscriptionService);
    authService = TestBed.inject(AuthService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('getUserSubscriptions() should return user subscriptions', fakeAsync(() => {
    let subscriptions: Subscription[];

    const mockSubs: Subscription[] = [
      {
        culturalOfferId: 1,
        culturalOfferTitle: 'Ponuda 1',
        guestId: 1001
      },
      {
        culturalOfferId: 2,
        culturalOfferTitle: 'Ponuda 2',
        guestId: 1001
      },
      {
        culturalOfferId: 3,
        culturalOfferTitle: 'Ponuda 3',
        guestId: 1001
      }
    ];

    subsService.getUserSubscriptions().subscribe(data => {
      subscriptions = data;
    });

    const req = httpMock.expectOne(`api/subscription/user/1001`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubs);

    tick();

    expect(subscriptions.length).toEqual(3, 'should contain given amount of subscriptions');
    expect(subscriptions[0].culturalOfferId).toEqual(1);
    expect(subscriptions[0].culturalOfferTitle).toEqual('Ponuda 1');
    expect(subscriptions[0].guestId).toEqual(1001);

    expect(subscriptions[1].culturalOfferId).toEqual(2);
    expect(subscriptions[1].culturalOfferTitle).toEqual('Ponuda 2');
    expect(subscriptions[1].guestId).toEqual(1001);

    expect(subscriptions[2].culturalOfferId).toEqual(3);
    expect(subscriptions[2].culturalOfferTitle).toEqual('Ponuda 3');
    expect(subscriptions[2].guestId).toEqual(1001);
  }));

  it('unsubscribe() should unsubscribe user to selected offer', fakeAsync(() => {
    let subscriptions: Subscription[];
    let unsubscribed: boolean;
    const subscription: Subscription = {
      culturalOfferId: 1,
      culturalOfferTitle: 'Ponuda 1',
      guestId: 1001
    };

    const mockDeleteRes = {
      deleted: true
    };

    const mockSubsBefore: Subscription[] = [
      {
        culturalOfferId: 1,
        culturalOfferTitle: 'Ponuda 1',
        guestId: 1001
      },
      {
        culturalOfferId: 2,
        culturalOfferTitle: 'Ponuda 2',
        guestId: 1001
      },
      {
        culturalOfferId: 3,
        culturalOfferTitle: 'Ponuda 3',
        guestId: 1001
      }
    ];

    const mockSubsAfter: Subscription[] = [
      {
        culturalOfferId: 2,
        culturalOfferTitle: 'Ponuda 2',
        guestId: 1001
      },
      {
        culturalOfferId: 3,
        culturalOfferTitle: 'Ponuda 3',
        guestId: 1001
      }
    ];

    subsService.getUserSubscriptions().subscribe(data => {
      subscriptions = data;
    });

    let req = httpMock.expectOne(`api/subscription/user/1001`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubsBefore);

    tick();
    expect(subscriptions.length).toEqual(3, 'should contain given amount of subscriptions before unsubscription');

    subsService.unsubscribe(subscription).subscribe(data => {
      unsubscribed = data.deleted;
    });

    req = httpMock.expectOne(`api/subscription`);
    expect(req.request.method).toBe('DELETE');
    req.flush(mockDeleteRes);

    tick();
    expect(unsubscribed).toEqual(true);

    subsService.getUserSubscriptions().subscribe(data => {
      subscriptions = data;
    });

    req = httpMock.expectOne(`api/subscription/user/1001`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubsAfter);

    tick();
    expect(subscriptions.length).toEqual(2, 'should contain given amount of subscriptions after unsubscription');
  }));

  it('subscribe() should subscribe user to selected offer', fakeAsync(() => {
    let subscriptions: Subscription[];
    let subscription: Subscription = {
      culturalOfferId: 1,
      culturalOfferTitle: 'Ponuda 1',
      guestId: 1001
    };

    const mockSubscription: Subscription = {
      culturalOfferId: 1,
      culturalOfferTitle: 'Ponuda 1',
      guestId: 1001
    };

    const mockSubsAfter: Subscription[] = [
      {
        culturalOfferId: 1,
        culturalOfferTitle: 'Ponuda 1',
        guestId: 1001
      },
      {
        culturalOfferId: 2,
        culturalOfferTitle: 'Ponuda 2',
        guestId: 1001
      },
      {
        culturalOfferId: 3,
        culturalOfferTitle: 'Ponuda 3',
        guestId: 1001
      }
    ];

    const mockSubsBefore: Subscription[] = [
      {
        culturalOfferId: 2,
        culturalOfferTitle: 'Ponuda 2',
        guestId: 1001
      },
      {
        culturalOfferId: 3,
        culturalOfferTitle: 'Ponuda 3',
        guestId: 1001
      }
    ];

    subsService.getUserSubscriptions().subscribe(data => {
      subscriptions = data;
    });

    let req = httpMock.expectOne(`api/subscription/user/1001`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubsBefore);

    tick();
    expect(subscriptions.length).toEqual(2, 'should contain given amount of subscriptions before subscription');

    subsService.subscribe(subscription).subscribe(data => {
      subscription = data;
    });

    req = httpMock.expectOne(`api/subscription`);
    expect(req.request.method).toBe('POST');
    req.flush(mockSubscription);

    tick();
    expect(subscription).toBeDefined();
    expect(subscription.culturalOfferId).toEqual(1);
    expect(subscription.culturalOfferTitle).toEqual('Ponuda 1');
    expect(subscription.guestId).toEqual(1001);

    subsService.getUserSubscriptions().subscribe(data => {
      subscriptions = data;
    });

    req = httpMock.expectOne(`api/subscription/user/1001`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubsAfter);

    tick();
    expect(subscriptions.length).toEqual(3, 'should contain given amount of subscriptions after subscription');
  }));
});
