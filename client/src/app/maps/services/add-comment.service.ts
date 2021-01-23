import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentInt } from '../model/comment';

@Injectable({
  providedIn: 'root'
})
export class AddCommentService {

  private readonly port = "http://localhost:8080"
  private readonly addcomment = "/c";
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  addGrade(comment:CommentInt): Observable<CommentInt> {
    
    return this.http.post<CommentInt>(this.port + this.addcomment, comment,
      {headers: this.headers, responseType: 'json'})  
  }
}
