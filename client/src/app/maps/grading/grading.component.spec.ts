import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { GradesService } from '../services/grades.service';

import { GradingComponent } from './grading.component';

describe('GradingComponent', () => {
  let component: GradingComponent;
  let fixture: ComponentFixture<GradingComponent>;
  let gradingService: GradesService;
  let auth : AuthService;

  beforeEach(async(() => {
    const grdServiceMock = {
      addGrade: jasmine.createSpy('addGrade')
        .and.returnValue(of({body: {
          id: 1,
          value:2,
          gradedOn: new Date(),
          userId:1,
          culturalOfferId:1
        }})),
      getSpecificGrade: jasmine.createSpy('getSpecificGrade')
        .and.returnValue(of(3))
    };

    const authServiceMock = {
      getUserId: jasmine.createSpy('getUserId')
        .and.returnValue(1001),
      getRole: jasmine.createSpy('getRole')
        .and.returnValue('ROLE_GUEST'),
      getToken: jasmine.createSpy('getToken')
        .and.returnValue("GG"),
      isLoggedIn: jasmine.createSpy('isLoggedIn')
        .and.returnValue(true)
    };

    TestBed.configureTestingModule({
      declarations: [ GradingComponent ],
      imports: [
        HttpClientTestingModule
      ],
      providers: [{ provide: GradesService, useValue: grdServiceMock }, 
        {
          provide: AuthService, useValue: authServiceMock
        }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GradingComponent);
    component = fixture.componentInstance;
    gradingService = TestBed.inject(GradesService);
    auth = TestBed.inject(AuthService);

    
  }));

  /*beforeEach(() => {
    fixture = TestBed.createComponent(GradingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });*/

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('add create', () => {
    component.culturalOffer = {
      id: 1,
      title: '1',
      description: '1',
      address: '',
      subTypeName: '',
      offerType: '',
      imageUrls: [],
      longitude: 0.1,
      latitude: 0.1
    };

    component.ngOnInit();

    component.gradeOffer();
    expect(gradingService.addGrade).toHaveBeenCalled();

  });

  it('add create', () => {
    component.culturalOffer = {
      id: 1,
      title: '1',
      description: '2',
      address: '',
      subTypeName: '',
      offerType: '',
      imageUrls: [],
      longitude: 0.1,
      latitude: 0.1
    };

    component.ngOnInit();

    expect(gradingService.getSpecificGrade).toHaveBeenCalled();

  });
});
