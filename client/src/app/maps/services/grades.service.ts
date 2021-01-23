import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Grade } from '../model/grade';

@Injectable({
  providedIn: 'root'
})
export class GradesService {

  private readonly port = "http://localhost:8080"
  private readonly avggrade = "/g/culturaloffer/averagegrade";
  private readonly specificgrade = "/g/culturaloffer/specific";
  private readonly addgrade = "/g";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getAvgGrade(id:string): Observable<number> {
    
    return this.http.get<number>(this.port + this.avggrade + `/${id}`,
      {headers: this.headers, responseType: 'json'})  
  }

  getSpecificGrade(offerId:string, userId: number): Observable<number> {
    
    return this.http.get<number>(this.port + this.specificgrade + `/${offerId}` + `/${userId}`,
      {headers: this.headers, responseType: 'json'})  
  }

  addGrade(grade:Grade): Observable<Grade> {
    
    return this.http.post<Grade>(this.port + this.addgrade, grade,
      {headers: this.headers, responseType: 'json'})  
  }
}
