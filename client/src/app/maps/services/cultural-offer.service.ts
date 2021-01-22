import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { CulturalOffer } from '../model/cultural-offer';
import { Zoom } from '../model/zoom';

@Injectable({
  providedIn: 'root'
})
export class CulturalOfferService {

  private readonly port = "http://localhost:8080"
  private readonly path = "/offers/filtering";
  private readonly general = "/offers";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  filter(zoom:Zoom):Observable<CulturalOffer[]> {
    //const params:HttpParams = new HttpParams().set('entry',entryText);
    return this.http.post<CulturalOffer[]>(this.port + this.path,{latitudeLowerCorner: zoom.latitudeLowerCorner,
      latitudeUpperCorner: zoom.latitudeUpperCorner,
      longitudeLowerCorner: zoom.longitudeLowerCorner,
      longitudeUpperCorner: zoom.longitudeUpperCorner
      },
      {headers: this.headers, responseType: 'json'}); 
  }

  add(offer:any):Observable<CulturalOffer>{
    return this.http.post<CulturalOffer>(this.port+this.general, offer, {headers: this.headers, responseType: 'json'});
  }
  
  update(id:number, offer:any):Observable<CulturalOffer>{
    return this.http.put<CulturalOffer>(this.port+this.general+"/"+id, offer, {headers: this.headers, responseType: 'json'});
  }

  getAll():Observable<CulturalOffer[]>{
    return this.http.get<CulturalOffer[]>(this.port+this.general, {headers: this.headers, responseType: 'json'});
  }

  delete(id:number):Observable<any>{
    return this.http.delete<void>(this.port+this.general+"/"+id, {headers: this.headers, responseType: 'json'});
  }
}
