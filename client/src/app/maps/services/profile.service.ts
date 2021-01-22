import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Guest } from '../model/guest';
import { Password } from '../model/password';

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
    return this.http.put<any>(this.port + this.path + `/${id}` + '/change-password', password, { observe: 'response' });
  }

  update(guest: Guest, id: number): Observable<any> {
    return this.http.put<Guest>(this.port + this.path + `/${id}`, guest, { observe: 'response' });
  }

}
