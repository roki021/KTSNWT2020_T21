import { Component, Input, OnInit } from '@angular/core';
import { CommentInt } from '../model/comment';
import { CulturalOffer } from '../model/cultural-offer';
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
  @Input() public culturalOffer : CulturalOffer;

  constructor(private auth_service: AuthService, private commentService: AddCommentService) {
    this.userId = auth_service.getUserId();
   }

  ngOnInit(): void {
    this.commentService.getCommentByOffer(this.culturalOffer.id).subscribe((res) => {
      this.comments = res;
      console.log(this.comments);
    });
  }

  deleteComment(id){
    this.commentService.deleteComment(id).subscribe(() => {
      
    });
    this.comments = this.comments.filter(item => item.id !== id);
  }

  onAdded(added: CommentInt) {
    added.commentedOn = new Date(added.commentedOn);
    this.comments.splice(0,0, added);
    
    //this.comments.push(added);
    console.log(added);
  }

}
