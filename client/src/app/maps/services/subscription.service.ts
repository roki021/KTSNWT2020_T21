import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Subscription } from '../model/subscription';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUserSubscriptions(): Observable<Subscription[]> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'body'
    };

    return this.http.get<Subscription[]>('api/subscription/user' + `/${this.authService.getUserId()}`,
      queryParams);
  }

  unsubscribe(subscription: Subscription): Observable<any> {
    return this.http.request('DELETE', 'api/subscription', { headers: this.headers, responseType: 'json', body: subscription });
  }

  subscribe(subscription: Subscription): Observable<Subscription> {
    return this.http.request<Subscription>('POST', 'api/subscription',
    {
      headers: this.headers,
      responseType: 'json',
      body: subscription,
      observe: 'body'
    });
  }
}
