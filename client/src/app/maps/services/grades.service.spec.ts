import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { Grade } from '../model/grade';

import { GradesService } from './grades.service';

describe('GradesService', () => {
  let service: GradesService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
        TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [GradesService]
      });

      injector = getTestBed();
      service = TestBed.inject(GradesService);
      httpClient = TestBed.inject(HttpClient);
      httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('add grade', fakeAsync(() => {
    let grade: Grade; 
    
    const newType: Grade = 
    {
      value:6,
      gradedOn: new Date(),
      userId:1001,
      culturalOfferId:14
    }; 
    
    const mockType: Grade = 
    {
      id:1,
      value:6,
      gradedOn: new Date(),
      userId:1001,
      culturalOfferId:14
    };      

    service.addGrade(newType).subscribe(res => grade = res);

    const req = httpMock.expectOne('http://localhost:8080/g');
    expect(req.request.method).toBe('POST');
    req.flush(mockType);

    tick();
    
    expect(grade).toBeDefined();
    expect(grade.id).toEqual(1);
    expect(grade.value).toEqual(6);
    expect(grade.userId).toEqual(1001);
    expect(grade.culturalOfferId).toEqual(14);

  }));


  it('get average grade', fakeAsync(() => {
    let grade: number; 

    service.getAvgGrade(1).subscribe(res => grade = res);

    const req = httpMock.expectOne('http://localhost:8080/g/culturaloffer/averagegrade/1');
    expect(req.request.method).toBe('GET');
    req.flush(8);

    tick();
    
    expect(grade).toBeDefined();
    expect(grade).toEqual(8);

  }));

  it('get specific grade', fakeAsync(() => {
    let grade: number; 

    service.getSpecificGrade(1, 1001).subscribe(res => grade = res);

    const req = httpMock.expectOne('http://localhost:8080/g/culturaloffer/specific/1/1001');
    expect(req.request.method).toBe('GET');
    req.flush(4);

    tick();
    
    expect(grade).toBeDefined();
    expect(grade).toEqual(4);

  }));
});
