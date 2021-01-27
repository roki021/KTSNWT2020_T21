import { HttpClient } from '@angular/common/http';
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick } from '@angular/core/testing';
import { OfferTypeService } from './offer-type.service';
import { OfferType } from '../model/offer-type';

describe('OfferTypeService', () => {
  let service: OfferTypeService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OfferTypeService]
    });

    injector = getTestBed();
    service = TestBed.inject(OfferTypeService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getPage() should return some types', fakeAsync(() => {
    let types: OfferType[];

    const mockTypes: OfferType[] = [
      {
        id: 1,
        name: 'park',
        subtypesNumber: 2,
        subtypes: ['muzej', 'pozoriste']
      },
      {
        id: 2,
        name: 'istorijsko mesto',
        subtypesNumber: 3,
        subtypes: ['nacionalni', 'akva', 'biblioteka']
      }];

    service.getPage(0, 2).subscribe(data => {
      types = data.body;
    });

    const req = httpMock.expectOne('http://localhost:8080/offer-types/by-page?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush(mockTypes);

    tick();

    expect(types.length).toEqual(2, 'should contain given amount of students');
    expect(types[0].id).toEqual(1);
    expect(types[0].name).toEqual('park');
    expect(types[0].subtypesNumber).toEqual(2);
    expect(types[0].subtypes[0]).toEqual('muzej');

    expect(types[1].id).toEqual(2);
    expect(types[1].name).toEqual('istorijsko mesto');
    expect(types[1].subtypesNumber).toEqual(3);
    expect(types[1].subtypes[0]).toEqual('nacionalni');

  }));

  it('getById() should query url and get a type by id', fakeAsync(() => {
    let type: OfferType;   
    const mockType: OfferType = 
    {
      id: 1,
      name: 'park',
      subtypesNumber: 2,
      subtypes: ['muzej', 'pozoriste']            
    };

    service.getById('1').subscribe(res => type = res.body);

    const req = httpMock.expectOne('http://localhost:8080/offer-types/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockType);

    tick();
    
    expect(type).toBeDefined();
    expect(type.id).toEqual(1);
    expect(type.name).toEqual('park');
    expect(type.subtypesNumber).toEqual(2);
    expect(type.subtypes[0]).toEqual('muzej');
    expect(type.subtypes[1]).toEqual('pozoriste');

  }));
  
  it('create() should query url and save a type', fakeAsync(() => {
    let newType: OfferType = {
      name: 'nov',
      subtypesNumber: 0,
      subtypes: [] 
    };

    const mockType: OfferType = 
    {
      id: 1,
      name: 'nov',
      subtypesNumber: 0,
      subtypes: []            
    };

    service.create(newType).subscribe(res => newType = res.body);
    
    const req = httpMock.expectOne('http://localhost:8080/offer-types');
    expect(req.request.method).toBe('POST');
    req.flush(mockType);

    tick();
    expect(newType).toBeDefined();
    expect(newType.id).toEqual(1);
    expect(newType.name).toEqual('nov');
    expect(newType.subtypesNumber).toEqual(0);
    expect(newType.subtypes.length).toEqual(0);
  }));

  it('update() should query url and edit a type', fakeAsync(() => {
    let type: OfferType = {  
      id: 1,
      name: 'park',
      subtypesNumber: 2,
      subtypes: ['muzej', 'pozoriste'] 
    };

    const mockType: OfferType = 
    {
      id: 1,
      name: 'park',
      subtypesNumber: 2,
      subtypes: ['muzej', 'pozoriste']             
    };
    
    service.update(type, 1).subscribe(res => type = res.body);
    
    const req = httpMock.expectOne('http://localhost:8080/offer-types/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockType);
    
    tick();

    expect(type).toBeDefined();
    expect(type.id).toEqual(1);
    expect(type.name).toEqual('park');
    expect(type.subtypesNumber).toEqual(2);
    expect(type.subtypes[0]).toEqual('muzej');
    expect(type.subtypes[1]).toEqual('pozoriste');
  }));

  it('delete() should query url and delete a type', () => {
    service.delete(1).subscribe(res => { });
    
    const req = httpMock.expectOne('http://localhost:8080/offer-types/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});
