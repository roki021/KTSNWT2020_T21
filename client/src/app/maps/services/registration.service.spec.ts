import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { async, ComponentFixture, fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Guest } from '../model/guest';
import { RegistrationComponent } from '../registration/registration.component';
import { AddOfferTypeComponent } from '../types/add-offer-type/add-offer-type.component';
import { AuthService } from './auth.service';
import { OfferTypeService } from './offer-type.service';

import { RegistrationService } from './registration.service';

describe('RegistrationService', () => {
  let service: RegistrationService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RegistrationService]
    });

    injector = getTestBed();
    service = TestBed.inject(RegistrationService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('Valid registration', fakeAsync(() => {
    let answer;
    service.register("realdt", "12345","Donald", "Trump", "test@test.com").subscribe(res => answer = res);

    const mockProfile: Guest = 
    {
      emailAddress: 'perica@mail.com',
      firstName: 'Petar',
      lastName: 'Petrović',
      password: undefined,
      username: 'perica',
      id: 1001            
    };

    const req = httpMock.expectOne('http://localhost:8080/auth/register');
    expect(req.request.method).toBe('POST');
    req.flush(mockProfile);

    tick();

    expect(answer).toBeTrue();
  }));

  it('Invalid registration', fakeAsync(() => {
    let registered;
    service.register("perica", "12345","Donald", "Trump", "test@test.com").subscribe(
      response => {
        registered = response;
      },
      error => {
        //loggedIn = false;
      });

    const mockProfile: Guest = 
    {
      emailAddress: 'perica@mail.com',
      firstName: 'Petar',
      lastName: 'Petrović',
      password: undefined,
      username: 'perica',
      id: 1001            
    };

    const req = httpMock.expectOne('http://localhost:8080/auth/register');
    expect(req.request.method).toBe('POST');
    req.flush({}, { status: 400, statusText: 'Username or email already in use'});

    tick();

    expect(registered).toBeUndefined();
  }));
});
