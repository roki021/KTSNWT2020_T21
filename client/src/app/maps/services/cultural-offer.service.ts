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

  private readonly port = 'http://localhost:8080';
  private readonly path = '/offers/filtering';
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  filter(zoom: Zoom): Observable<CulturalOffer[]> {
    return this.http.post<CulturalOffer[]>(this.port + this.path, {
      latitudeLowerCorner: zoom.latitudeLowerCorner,
      latitudeUpperCorner: zoom.latitudeUpperCorner,
      longitudeLowerCorner: zoom.longitudeLowerCorner,
      longitudeUpperCorner: zoom.longitudeUpperCorner
    },
      { headers: this.headers, responseType: 'json' });
  }

}
