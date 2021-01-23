import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Subscription } from '../model/subscription';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private readonly port = "http://localhost:8080"
  private readonly path = "/subscription/user";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUserSubscriptions(): Observable<Subscription[]> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'body'
    };

    return this.http.get<Subscription[]>(this.port + this.path + `/${this.authService.getUserId()}`,
      queryParams);
  }

  unsubscribe(subscription: Subscription): Observable<any> {
    return this.http.request('DELETE', this.port + '/subscription', { headers: this.headers, responseType: 'json', body: subscription });
  }

  subscribe(subscription: Subscription): Observable<Subscription> {
    return this.http.request<Subscription>('POST', this.port + '/subscription', 
    { 
      headers: this.headers, 
      responseType: 'json', 
      body: subscription, 
      observe: 'body'
    });
  }
}
