import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { RegistrationService } from '../services/registration.service';

import { RegistrationComponent } from './registration.component';

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;
  let regService: RegistrationService;
  let fb : FormBuilder;

  beforeEach(async(() => {
    const regServiceMock = {
      register: jasmine.createSpy('register')
        .and.returnValue(of(true))
    };

    TestBed.configureTestingModule({
      declarations: [ RegistrationComponent ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [{ provide: RegistrationService, useValue: regServiceMock }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    regService = TestBed.inject(RegistrationService);
    fb = TestBed.inject(FormBuilder);
  }));

  /*beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });*/

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('register', () => {
    component.ngOnInit();

    component.form = fb.group({
      firstname: ['1'],
      lastname: ['1'],
      email: ['2@2.com'],
      username: ['rew'],
      password: ['12'],
      repeatpassword: ['12']
    });
    component.register();
    expect(regService.register).toHaveBeenCalled();
  });

  it('invalid register', () => {
    component.ngOnInit();

    component.form = fb.group({
      firstname: ['1'],
      lastname: ['1'],
      email: ['2@2.com'],
      username: ['rew'],
      password: ['12'],
      repeatpassword: ['123']
    });
    component.register();
    expect(regService.register).not.toHaveBeenCalled();
  });
});
