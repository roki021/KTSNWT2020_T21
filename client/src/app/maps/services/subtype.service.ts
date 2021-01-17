import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subtype } from '../model/subtype';

@Injectable({
  providedIn: 'root'
})
export class SubtypeService {

  private readonly port = "http://localhost:8080"
  private readonly path = "/subtypes/";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getPage(page:number, size:number, offer_type:string):Observable<any> {
    let queryParams = {};

		queryParams = {
			headers: this.headers,
			observe: 'response'
    };
    return this.http.get<Subtype[]>(this.port + this.path + offer_type + "/by-page?page="+ 
    page + "&size=" + size, queryParams);
  }
}
