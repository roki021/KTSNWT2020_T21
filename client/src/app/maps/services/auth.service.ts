import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserToken } from '../model/user-token';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  private currentUser: UserToken;

  constructor(private http: HttpClient) {
    if (localStorage.getItem('user')) {
      this.currentUser = JSON.parse(localStorage.getItem('user'));
    }
  }

  login(username: string, password: string): Observable<boolean> {
    // const params:HttpParams = new HttpParams().set('entry',entryText);
    return this.http.post('api/auth/login', JSON.stringify({ username, password }),
      { headers: this.headers, responseType: 'json' }).pipe(
        map((res: any) => {
          console.log(res);
          const token = res && res.accessToken;

          if (token) {
            const jwt: JwtHelperService = new JwtHelperService();
            const info = jwt.decodeToken(token);
            const userToken: UserToken = {
              id: info.user_id,
              username: info.sub,
              expireIn: info.exp * 1000,
              authorities: info.roles.map((role) => role.authority),
              token
            };
            this.currentUser = userToken;
            localStorage.setItem('user', JSON.stringify(userToken));
            return true;
          } else {
            return false;
          }
        }),
        catchError(error => {
          if (error.status === 401) {
            return throwError('Ilegal login');
          } else {
            return throwError('Server error');
          }
        }));
  }

  refreshToken(): Observable<boolean> {
    return this.http.post('api/auth/refresh', {}).pipe(
      map((res: any) => {
        console.log(res);
        const token = res && res.accessToken;

        if (token) {
          const jwt: JwtHelperService = new JwtHelperService();
          const info = jwt.decodeToken(token);
          const userToken: UserToken = {
            id: info.user_id,
            username: info.sub,
            expireIn: info.exp * 1000,
            authorities: info.roles.map((role) => role.authority),
            token
          };
          this.currentUser = userToken;
          localStorage.setItem('user', JSON.stringify(userToken));
          return true;
        } else {
          return false;
        }
      }),
      catchError(error => {
        return throwError('Token could not be refreshed.');
      }));
  }

  getUserId(): number {
    return this.currentUser ? this.currentUser.id : null;
  }

  getToken(): string {
    return this.currentUser ? this.currentUser.token : null;
  }

  getRole(): string {
    return this.currentUser ? this.currentUser.authorities[0] : null;
  }

  logout(): void {
    localStorage.removeItem('user');
    this.currentUser = null;
  }

  isLoggedIn(): boolean {
    if (localStorage.getItem('user')) {
      return true;
    } else {
      return false;
    }
  }

  getCurrentUser(): UserToken {
   return this.currentUser;
  }
}
