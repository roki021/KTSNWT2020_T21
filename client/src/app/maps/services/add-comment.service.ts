import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommentInt } from '../model/comment';

@Injectable({
  providedIn: 'root'
})
export class AddCommentService {

  private readonly port = "http://localhost:8080"
  private readonly addcomment = "/c";
  private readonly getbyoffer = "/c/culturaloffer/comments";

	private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  addCommentCall(comment:CommentInt): Observable<CommentInt> {
    
    return this.http.post<CommentInt>(this.port + this.addcomment, comment,
      {headers: this.headers, responseType: 'json'});  
  }

  deleteComment(id:number) {
    console.log("usao");
    return this.http.delete(this.port + this.addcomment + `/${id}`,
      {headers: this.headers, responseType: 'json'});  
  }

  getCommentByOffer(offerId:number): Observable<CommentInt[]> {
    
    return this.http.get<CommentInt[]>(this.port + this.getbyoffer + `/${offerId}`,
      {headers: this.headers, responseType: 'json'}).pipe(map((res: CommentInt[]) => {
          var pompom : CommentInt[];
          if(res) {
            var i;
            var pom;
            for (i = 0; i < res.length; i++) {
              pom = res[i].commentedOn
              res[i].commentedOn = new Date(pom);
            } 
            return res;
          }
          else
            return pom;
        })
      );
  }
}
