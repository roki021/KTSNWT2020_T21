import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private readonly port = "http://localhost:8080"
  private readonly path = "/auth/register";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  register(username: string, password: string, firstName: string, lastName: string,
    emailAddress: string): Observable<boolean> {
    //const params:HttpParams = new HttpParams().set('entry',entryText);
    return this.http.post(this.port + this.path, JSON.stringify({username, password, firstName, lastName, emailAddress}),
      {headers: this.headers, responseType: 'json'}).pipe(
      map((res: any) => {
        
        return true;
      }),
      catchError(error => {
        if (error.status === 400) {
          return throwError('Username or email already in use');
        }
        else {
          return throwError ('Server error');
        }
      }))
  }

  confirmRegistration(token: string): Observable<boolean> {
    return this.http.get('api/auth/registrationConfirm?token=' + token).pipe(
      map(res => {
        return true;
      }),
      catchError(error => {
        if (error.status === 404) {
          return throwError('User not found!');
        }
        else {
          return throwError ('Verification token expired!');
        }
      })
    );
  }
}
