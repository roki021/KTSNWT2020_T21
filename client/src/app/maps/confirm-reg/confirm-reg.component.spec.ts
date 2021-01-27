import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { RegistrationService } from '../services/registration.service';

import { ConfirmRegComponent } from './confirm-reg.component';

describe('ConfirmRegComponent', () => {
  let component: ConfirmRegComponent;
  let fixture: ComponentFixture<ConfirmRegComponent>;
  let regService: RegistrationService;
  let activeRoute: ActivatedRoute;

  beforeEach(async(() => {
    const regServiceMock = {
      confirmRegistration: jasmine.createSpy('confirmRegistration')
        .and.returnValue(of(true))
    };

    const activeRouteMock = {
      queryParams: of({token: ''})
    };

    TestBed.configureTestingModule({
      declarations: [ ConfirmRegComponent ],
      providers: [
        {
          provide: RegistrationService, useValue: regServiceMock
        },
        {
          provide: ActivatedRoute, useValue: activeRouteMock
        }
      ]
    });

    fixture = TestBed.createComponent(ConfirmRegComponent);
    component = fixture.componentInstance;
    regService = TestBed.inject(RegistrationService);
    activeRoute = TestBed.inject(ActivatedRoute);
  }));

  it('should show successful verification message', async(() => {
    component.ngOnInit();
    expect(regService.confirmRegistration).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.isVerified).toBe(true);
        fixture.detectChanges();
        const element: DebugElement =
          fixture.debugElement.query(By.css('a'));
        expect(element).toBeDefined();
      });
  }));

  it('should show unsuccessful verification message', async(() => {
    component.ngOnInit();
    expect(regService.confirmRegistration).toHaveBeenCalled();
    component.isVerified = false;
    component.badMessage = 'message';

    fixture.whenStable()
      .then(() => {
        expect(component.isVerified).toBe(false);
        fixture.detectChanges();
        const element: DebugElement =
          fixture.debugElement.query(By.css('h3'));
        expect(element).toBeDefined();
      });
  }));
});
