import { Component, OnInit } from '@angular/core';
import { CommentInt } from '../model/comment';
import { AddCommentService } from '../services/add-comment.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-comment-cards',
  templateUrl: './comment-cards.component.html',
  styleUrls: ['./comment-cards.component.sass']
})
export class CommentCardsComponent implements OnInit {

  public comments : CommentInt[] = [];
  public userId : number;
  public offerId : number = 14;

  constructor(private auth_service: AuthService, private commentService: AddCommentService) {
    this.userId = auth_service.getUserId();
   }

  ngOnInit(): void {
    this.commentService.getCommentByOffer(this.offerId).subscribe((res) => {
      this.comments = res;
      console.log(this.comments);
    });
  }

  deleteComment(id){
    this.commentService.deleteComment(id).subscribe(() => {
      
    });
    this.comments = this.comments.filter(item => item.id !== id);
  }

}
