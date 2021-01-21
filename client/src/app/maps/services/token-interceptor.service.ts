import { HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginServiceService } from './login-service.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService {

  constructor(private inj: Injector) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authenticationService:LoginServiceService = this.inj.get(LoginServiceService); 
    request = request.clone({
      setHeaders: {
        'Authorization': `Bearer ${authenticationService.getToken()}`
      }
    });

    return next.handle(request);
  }
}
