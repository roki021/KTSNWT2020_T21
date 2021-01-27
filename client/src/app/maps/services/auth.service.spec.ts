import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserToken } from '../model/user-token';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  let authService: AuthService;
  let jwt: JwtHelperService;

  beforeEach(() => {
    const jwtMockService = {
      decodeToken: jasmine.createSpy('decodeToken')
        .and.returnValue({
          id: 1001,
          username: 'perica',
          expireIn: new Date().getTime() + 18000000,
          authorities: ['ROLE_GUEST'],
          token: 'token'
        })
    };

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        AuthService,
        { provide: JwtHelperService, useValue: jwtMockService }
      ]
    });

    injector = getTestBed();
    authService = injector.inject(AuthService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
    jwt = TestBed.inject(JwtHelperService);

    let store = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in store ? store[key] : null;
      },
      setItem: (key: string, value: string) => {
        store[key] = `${value}`;
      },
      removeItem: (key: string) => {
        delete store[key];
      },
      clear: () => {
        store = {};
      }
    };
    spyOn(localStorage, 'getItem')
      .and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem')
      .and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'removeItem')
      .and.callFake(mockLocalStorage.removeItem);
    spyOn(localStorage, 'clear')
      .and.callFake(mockLocalStorage.clear);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('login() log in user with correct info into application', fakeAsync(() => {
    let userToken: UserToken;
    let loggedIn: boolean;
    const mockUserToken = {
      accessToken: `
        eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJhbC1vZmZlcnMtbWFwIiwic3ViIjoicGVyaWNhIi
        widXNlcl9pZCI6IjEwMDEiLCJyb2xlcyI6W3siaWQiOjIsIm5hbWUiOiJST0xFX0dVRVNUIiwiYXV0aG9yaXR5IjoiUk9M
        RV9HVUVTVCJ9XSwiYXVkIjoid2ViIiwiaWF0IjoxNjExNjg5ODI2LCJleHAiOjE2MTE3MDc4MjZ9.5DEycxkIHT-vNvrcADsmsPT0ckkFk0n
        aySTPwt4nS7mSNIQWIiCNGt0VUaSWrdSLQDBnLqAzFVVSdzDLikOuOw
      `,
      expiresIn: 18000000
    };

    authService.login('perica', '12345').subscribe(response => {
      loggedIn = response;
      const user = {
        id: 1001,
        username: 'perica',
        expireIn: new Date().getTime() + 18000000,
        authorities: ['ROLE_GUEST'],
        token: 'token'
      };
      localStorage.setItem('user', JSON.stringify(user));
    });

    const req = httpMock.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockUserToken);

    tick();
    userToken = authService.getCurrentUser();
    expect(loggedIn).toEqual(true);
    expect(userToken).toBeDefined();
    expect(userToken.id).toEqual(1001);
    expect(userToken.username).toEqual('perica');
    expect(userToken.authorities).toEqual(['ROLE_GUEST']);
  }));

  it('login() log in user with wrong credentials', fakeAsync(() => {
    let loggedIn: boolean;

    authService.login('perica', '25251').subscribe(
      response => {
        loggedIn = response;
      },
      error => {
        loggedIn = false;
      }
    );

    const req = httpMock.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush({}, { status: 401, statusText: 'Unauthorized' });

    tick();
    expect(authService.isLoggedIn()).toEqual(false);
    expect(loggedIn).toEqual(false);
  }));

  it('validateToken() checks token for expiration and log out user', fakeAsync(() => {
    const user = {
      id: 1001,
      username: 'perica',
      expireIn: new Date('2020-10-11T10:20:30Z').getTime() + 18000000,
      authorities: ['ROLE_GUEST'],
      token: 'token'
    };

    localStorage.setItem('user', JSON.stringify(user));

    expect(authService.isLoggedIn()).toEqual(true);

    authService.validateToken();

    tick();
    expect(authService.isLoggedIn()).toEqual(false);
  }));

  it('validateToken() checks token for expiration and do nothing', fakeAsync(() => {
    const user = {
      id: 1001,
      username: 'perica',
      expireIn: new Date().getTime() + 18000000,
      authorities: ['ROLE_GUEST'],
      token: 'token'
    };

    localStorage.setItem('user', JSON.stringify(user));

    expect(authService.isLoggedIn()).toEqual(true);

    authService.validateToken();

    tick();
    expect(authService.isLoggedIn()).toEqual(true);
  }));

  it('refreshToken() refreshes user token', fakeAsync(() => {
    let userToken: UserToken;
    let refreshed: boolean;
    const mockUserToken = {
      accessToken: `
        eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJhbC1vZmZlcnMtbWFwIiwic3ViIjoicGVyaWNhIi
        widXNlcl9pZCI6IjEwMDEiLCJyb2xlcyI6W3siaWQiOjIsIm5hbWUiOiJST0xFX0dVRVNUIiwiYXV0aG9yaXR5IjoiUk9M
        RV9HVUVTVCJ9XSwiYXVkIjoid2ViIiwiaWF0IjoxNjExNjg5ODI2LCJleHAiOjE2MTE3MDc4MjZ9.5DEycxkIHT-vNvrcADsmsPT0ckkFk0n
        aySTPwt4nS7mSNIQWIiCNGt0VUaSWrdSLQDBnLqAzFVVSdzDLikOuOw
      `,
      expiresIn: 18000000
    };

    authService.refreshToken().subscribe(response => {
      refreshed = response;
      const user = {
        id: 1001,
        username: 'perica',
        expireIn: new Date().getTime() + 18000000,
        authorities: ['ROLE_GUEST'],
        token: 'token'
      };
      localStorage.setItem('user', JSON.stringify(user));
    });

    const req = httpMock.expectOne('api/auth/refresh');
    expect(req.request.method).toBe('POST');
    req.flush(mockUserToken);

    tick();
    userToken = authService.getCurrentUser();
    expect(refreshed).toEqual(true);
    expect(userToken).toBeDefined();
    expect(userToken.id).toEqual(1001);
    expect(userToken.username).toEqual('perica');
    expect(userToken.authorities).toEqual(['ROLE_GUEST']);
  }));
});
