import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Zoom } from '../model/zoom';
import { Observable } from 'rxjs';
import { CulturalOffer } from '../model/cultural-offer';
import { LoginResponse } from '../model/login-response';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  private readonly port = "http://localhost:8080"
  private readonly path = "/auth/login";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    //const params:HttpParams = new HttpParams().set('entry',entryText);
    return this.http.post<LoginResponse>(this.port + this.path,{
        "username": username,
        "password": password
      },
      {headers: this.headers, responseType: 'json'})
  }
}
