import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { Subtype } from '../model/subtype';

import { SubtypeService } from './subtype.service';

describe('SubtypeService', () => {
  let service: SubtypeService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SubtypeService]
    });

    injector = getTestBed();
    service = TestBed.inject(SubtypeService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getPage() should return some subtypes', fakeAsync(() => {
    let subtypes: Subtype[];

    const mockSubtypes: Subtype[] = [
      {
        id: 1,
        name: 'akva',
        offerTypeName: 'park',
        offerNumber: 1
      },
      {
        id: 2,
        name: 'nacionalni',
        offerTypeName: 'park',
        offerNumber: 1
      }];

    service.getPage(0, 2, '1').subscribe(data => {
      subtypes = data.body;
    });

    const req = httpMock.expectOne('http://localhost:8080/subtypes/1/by-page?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush(mockSubtypes);

    tick();

    expect(subtypes.length).toEqual(2, 'should contain given amount of students');
    expect(subtypes[0].id).toEqual(1);
    expect(subtypes[0].name).toEqual('akva');
    expect(subtypes[0].offerTypeName).toEqual('park');
    expect(subtypes[0].offerNumber).toEqual(1);

    expect(subtypes[1].id).toEqual(2);
    expect(subtypes[1].name).toEqual('nacionalni');
    expect(subtypes[1].offerTypeName).toEqual('park');
    expect(subtypes[1].offerNumber).toEqual(1);

  }));

  it('create() should query url and save a type', fakeAsync(() => {
    let newSubtype: Subtype = {
      name: 'nov',
      offerTypeName: 'park',
      offerNumber: 0
    };

    const mockSubtype: Subtype = 
    {
      id: 1,
      name: 'nov',
      offerTypeName: 'park',
      offerNumber: 0          
    };

    service.create(newSubtype).subscribe(res => newSubtype = res.body);
    
    const req = httpMock.expectOne('http://localhost:8080/subtypes');
    expect(req.request.method).toBe('POST');
    req.flush(mockSubtype);

    tick();
    expect(newSubtype).toBeDefined();
    expect(newSubtype.id).toEqual(1);
    expect(newSubtype.name).toEqual('nov');
    expect(newSubtype.offerNumber).toEqual(0);
    expect(newSubtype.offerTypeName).toEqual('park');
  }));

  it('update() should query url and edit a type', fakeAsync(() => {
    let subtype: Subtype = {
      id: 1,
      name: 'akva',
      offerTypeName: 'park',
      offerNumber: 1
    };

    const mockType: Subtype = 
    {
      id: 1,
      name: 'akva',
      offerTypeName: 'park',
      offerNumber: 1
    };
    
    service.update(subtype, 1).subscribe(res => subtype = res.body);
    
    const req = httpMock.expectOne('http://localhost:8080/subtypes/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockType);
    
    tick();

    expect(subtype).toBeDefined();
    expect(subtype.id).toEqual(1);
    expect(subtype.name).toEqual('akva');
    expect(subtype.offerTypeName).toEqual('park');
    expect(subtype.offerNumber).toEqual(1);
  }));

  it('delete() should query url and delete a type', () => {
    service.delete(1).subscribe(res => { });
    
    const req = httpMock.expectOne('http://localhost:8080/subtypes/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('getAll() should return all subtypes', fakeAsync(() => {
    let subtypes: Subtype[];

    const mockSubtypes: Subtype[] = [
      {
        id: 1,
        name: 'akva',
        offerTypeName: 'park',
        offerNumber: 1
      },
      {
        id: 2,
        name: 'nacionalni',
        offerTypeName: 'park',
        offerNumber: 1
      }];

    service.getAll().subscribe(data => {
      subtypes = data.body;
    });

    const req = httpMock.expectOne('http://localhost:8080/subtypes');
    expect(req.request.method).toBe('GET');
    req.flush(mockSubtypes);

    tick();

    expect(subtypes.length).toEqual(2, 'should contain given amount of students');
    expect(subtypes[0].id).toEqual(1);
    expect(subtypes[0].name).toEqual('akva');
    expect(subtypes[0].offerTypeName).toEqual('park');
    expect(subtypes[0].offerNumber).toEqual(1);

    expect(subtypes[1].id).toEqual(2);
    expect(subtypes[1].name).toEqual('nacionalni');
    expect(subtypes[1].offerTypeName).toEqual('park');
    expect(subtypes[1].offerNumber).toEqual(1);

  }));
});
