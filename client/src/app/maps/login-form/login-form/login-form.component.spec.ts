import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { faBaby } from '@fortawesome/free-solid-svg-icons';
import { of } from 'rxjs';
import { AuthService } from '../../services/auth.service';

import { LoginFormComponent } from './login-form.component';

describe('LoginFormComponent', () => {
  let component: LoginFormComponent;
  let fixture: ComponentFixture<LoginFormComponent>;
  let loginService: AuthService;
  let fb : FormBuilder;

  beforeEach(async(() => {
    const authServiceMock = {
      login: jasmine.createSpy('login')
        .and.returnValue(of(true))
    };

    TestBed.configureTestingModule({
      declarations: [ LoginFormComponent ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [{ provide: AuthService, useValue: authServiceMock }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginFormComponent);
    component = fixture.componentInstance;
    loginService = TestBed.inject(AuthService);
    fb = TestBed.inject(FormBuilder);
  }));

  /*beforeEach(() => {
    fixture = TestBed.createComponent(LoginFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });*/

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('login', () => {
    component.ngOnInit();

    component.form = fb.group({
      username: ['perica'],
      password: ['12345']
    });
    component.login();
    expect(loginService.login).toHaveBeenCalled();
  });

  it('invalid login', () => {
    component.ngOnInit();

    component.form = fb.group({
      username: ['perica'],
      password: ['']
    });
    component.login();
    expect(loginService.login).not.toHaveBeenCalled();
  });
});
