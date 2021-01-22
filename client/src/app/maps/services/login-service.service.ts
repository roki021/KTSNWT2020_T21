import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Zoom } from '../model/zoom';
import { Observable } from 'rxjs';
import { CulturalOffer } from '../model/cultural-offer';
import { LoginResponse } from '../model/login-response';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  private readonly port = "http://localhost:8080"
  private readonly path = "/auth/login";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<boolean> {
    //const params:HttpParams = new HttpParams().set('entry',entryText);
    return this.http.post(this.port + this.path, JSON.stringify({username, password}),
      {headers: this.headers, responseType: 'json'}).pipe(
      map((res: any) => {
        let token = res && res['accessToken'];
        
        if (token) {
          localStorage.setItem('jwt', token);
          return true;
        }
        else {
          return false;
        }
      }),
      catchError(error => {
        if (error.status === 401) {
          return throwError('Ilegal login');
        }
        else {
          return throwError ('Server error');
        }
      }))
  }

  getToken(): String {
    var token = localStorage.getItem('jwt');
    return token ? token : "";
  }

  logout(): void {
    localStorage.removeItem('jwt');
  }

  isLoggedIn(): boolean {
    if (this.getToken() != '') return true;
    else return false;
  }

  /*getCurrentUser() {
    if (localStorage.currentUser) {
      return JSON.parse(localStorage.currentUser);
    }
    else {
      return undefined;
    }
  }*/
}
