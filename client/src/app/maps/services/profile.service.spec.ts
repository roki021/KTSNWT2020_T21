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
    let password: Password = 
    {
      oldPassword: '12345',
      newPassword: '123',
      repetedPassword: '123'        
    };
    const mockPassword: Password = 
    {
      oldPassword: '12345',
      newPassword: '123',
      repetedPassword: '123'        
    };
    
    service.changePassword(password, 1001).subscribe(res => password = res.body);
    
    const req = httpMock.expectOne('http://localhost:8080/profile/1001/change-password');
    expect(req.request.method).toBe('PUT');
    req.flush(mockPassword);
    
    tick();

    expect(password).toBeDefined();
    expect(password.oldPassword).toEqual('12345');
    expect(password.newPassword).toEqual('123');
    expect(password.repetedPassword).toEqual('123');
  }));
});
