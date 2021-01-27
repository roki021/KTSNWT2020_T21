import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { Guest } from '../model/guest';
import { Password } from '../model/password';

import { ProfileService } from './profile.service';

describe('ProfileService', () => {
  let service: ProfileService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProfileService]
    });
    injector = getTestBed();
    service = TestBed.inject(ProfileService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getProfile() should query url and get the user profile data', fakeAsync(() => {
    let profile: Guest;   
    const mockProfile: Guest = 
    {
      emailAddress: 'perica@mail.com',
      firstName: 'Petar',
      lastName: 'Petrović',
      password: undefined,
      username: 'perica',
      id: 1001            
    };

    service.getProfile(1001).subscribe(res => profile = res.body);

    const req = httpMock.expectOne('http://localhost:8080/profile/1001');
    expect(req.request.method).toBe('GET');
    req.flush(mockProfile);

    tick();
    
    expect(profile).toBeDefined();
    expect(profile.id).toEqual(1001);
    expect(profile.firstName).toEqual('Petar');
    expect(profile.lastName).toEqual('Petrović');
    expect(profile.emailAddress).toEqual('perica@mail.com');
    expect(profile.username).toEqual('perica');

  }));

  it('update() should query url and edit profile data', fakeAsync(() => {
    let profile: Guest = 
    {
      emailAddress: 'perica@mail.com',
      firstName: 'Petar',
      lastName: 'Petrović',
      password: undefined,
      username: 'perica',
      id: 1001            
    };   
    const mockProfile: Guest = 
    {
      emailAddress: 'perica@mail.com',
      firstName: 'Petar',
      lastName: 'Petrović',
      password: undefined,
      username: 'perica',
      id: 1001            
    };
    
    service.update(profile, 1001).subscribe(res => profile = res.body);
    
    const req = httpMock.expectOne('http://localhost:8080/profile/1001');
    expect(req.request.method).toBe('PUT');
    req.flush(mockProfile);
    
    tick();

    expect(profile).toBeDefined();
    expect(profile.id).toEqual(1001);
    expect(profile.firstName).toEqual('Petar');
    expect(profile.lastName).toEqual('Petrović');
    expect(profile.emailAddress).toEqual('perica@mail.com');
    expect(profile.username).toEqual('perica');
  }));

  it('changePassword() should query url and change the guest password', fakeAsync(() => {
    let isChanged: boolean;
    let password: Password = 
    {
      oldPassword: '12345',
      newPassword: '123',
      repetedPassword: '123'        
    };
    const mockUserToken = {
      accessToken: `
        eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJhbC1vZmZlcnMtbWFwIiwic3ViIjoicGVyaWNhIi
        widXNlcl9pZCI6IjEwMDEiLCJyb2xlcyI6W3siaWQiOjIsIm5hbWUiOiJST0xFX0dVRVNUIiwiYXV0aG9yaXR5IjoiUk9M
        RV9HVUVTVCJ9XSwiYXVkIjoid2ViIiwiaWF0IjoxNjExNjg5ODI2LCJleHAiOjE2MTE3MDc4MjZ9.5DEycxkIHT-vNvrcADsmsPT0ckkFk0n
        aySTPwt4nS7mSNIQWIiCNGt0VUaSWrdSLQDBnLqAzFVVSdzDLikOuOw
      `,
      expiresIn: 18000000
    };
    
    service.changePassword(password, 1001).subscribe(res => isChanged = res);
    
    const req = httpMock.expectOne('http://localhost:8080/profile/1001/change-password');
    expect(req.request.method).toBe('PUT');
    req.flush(mockUserToken);
    
    tick();

    expect(isChanged).toBeDefined();
    expect(isChanged).toBeTrue();
  }));
});
