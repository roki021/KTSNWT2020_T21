import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OfferType } from '../model/offer-type';

@Injectable({
  providedIn: 'root'
})
export class OfferTypeService {

  private readonly port = 'http://localhost:8080';
  private readonly path = '/offer-types';
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  getPage(page: number, size: number): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };

    return this.http.get<OfferType[]>(this.port + this.path + '/by-page?page=' + page + '&size=' + size,
      queryParams);
  }

  getById(id: string): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get<OfferType[]>(this.port + this.path + `/${id}`, queryParams);
  }

  create(offerType: OfferType): Observable<any> {
    return this.http.post<OfferType>(this.port + this.path, offerType, { observe: 'response' });
  }

  update(offerType: OfferType, id: number): Observable<any> {
    return this.http.put<OfferType>(this.port + this.path + `/${id}`, offerType, { observe: 'response' });
  }

  delete(id: any): Observable<any> {
    return this.http.delete(this.port + this.path + `/${id}`, { observe: 'response' });
  }
}
