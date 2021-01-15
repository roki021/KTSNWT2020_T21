import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OfferType } from '../model/offer-type';

@Injectable({
  providedIn: 'root'
})
export class OfferTypeService {

  private readonly port = "http://localhost:8080"
  private readonly path = "/offer-types";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getPage(page:number, size:number):Observable<any> {
    let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
    };
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      observe: 'response' as 'response'
    };
    return this.http.get<OfferType[]>(this.port + this.path + "/by-page?page="+ page + "&size=" + size,
     httpOptions);
  }
}
