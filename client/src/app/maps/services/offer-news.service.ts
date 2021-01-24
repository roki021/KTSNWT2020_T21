import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OfferNews } from '../model/offer-news';

@Injectable({
  providedIn: 'root'
})
export class OfferNewsService {

  private readonly port = "http://localhost:8080/news"
  private readonly path = "/offer/";
  private readonly pages = "/by-page/";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  add(news:any):Observable<OfferNews>{
    return this.http.post<OfferNews>(this.port, news, {headers: this.headers, responseType: 'json'});
  }
  
  update(id:number, news:any):Observable<OfferNews>{
    return this.http.put<OfferNews>(this.port+"/"+id, news, {headers: this.headers, responseType: 'json'});
  }

  getAll(offerId:number):Observable<OfferNews[]>{
    return this.http.get<OfferNews[]>(this.port+this.path+offerId, {headers: this.headers, responseType: 'json'});
  }

  getPage(offerId:number, page:number, size:number):Observable<OfferNews[]>{
    return this.http.get<OfferNews[]>(this.port+this.pages+offerId+'?page='+page+'&size='+size);
  }

  delete(id:number):Observable<any>{
    return this.http.delete<void>(this.port+"/"+id, {headers: this.headers, responseType: 'json'});
  }
}
