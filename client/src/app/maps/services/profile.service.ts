import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Guest } from '../model/guest';
import { Password } from '../model/password';
import { UserToken } from '../model/user-token';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private readonly port = 'http://localhost:8080';
  private readonly path = '/profile';
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  getProfile(id: number): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };

    return this.http.get<Guest>(this.port + this.path + `/${id}`,
      queryParams);
  }

  changePassword(password: Password, id: number): Observable<any> {
    return this.http.put<any>(this.port + this.path + `/${id}` + '/change-password', password, { observe: 'response' }).pipe(
      map((res: any) => {
        const token = res.body && res.body.accessToken;

        if (token) {
          const jwt: JwtHelperService = new JwtHelperService();
          const info = jwt.decodeToken(token);
          const userToken: UserToken = {
            id: parseInt(info.user_id, 10),
            username: info.sub,
            expireIn: info.exp * 1000,
            authorities: info.roles.map((role) => role.authority),
            token
          };
          localStorage.setItem('user', JSON.stringify(userToken));
          return true;
        } else {
          return false;
        }
      }),
      catchError(error => {
        return throwError(error);
      }));
  }

  update(guest: Guest, id: number): Observable<any> {
    return this.http.put<Guest>(this.port + this.path + `/${id}`, guest, { observe: 'response' });
  }

}
