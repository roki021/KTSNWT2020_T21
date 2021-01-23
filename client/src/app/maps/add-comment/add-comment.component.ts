import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommentInt } from '../model/comment';
import { AddCommentService } from '../services/add-comment.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.sass']
})
export class AddCommentComponent implements OnInit {

  images = [];
  public imagesBase64 = [];
  public submitValidate: boolean;
  public culturalOfferName = "ponuda";
  public userId : number;
  public commentadd: CommentInt;
  public offerId : number = 14;
  public notAnImage : boolean;

  myForm = new FormGroup({
   content: new FormControl('', Validators.required),
   file: new FormControl('', Validators.required),
   fileSource: new FormControl('')
 });

 constructor(private auth_service: AuthService, private commentService: AddCommentService) {
   this.commentadd = {content: '', userId : auth_service.getUserId(), commentedOn: new Date(),
        imageUrls: [], culturalOfferId: this.offerId};
  }

 ngOnInit(): void {
 }

 get f(){
   return this.myForm.controls;
 }

 onFileChange(event) {
  this.notAnImage = false;
  if (event.target.files && event.target.files[0]) {
       var filesAmount = event.target.files.length;

       for (let i = 0; i < filesAmount; i++) {
               var reader = new FileReader();
               reader.onload = (event:any) => {
                  //console.log(event.target.result.substring(event.target.result.indexOf("base64,") + 7));
                  //console.log(event.target.result);
                  if(event.target.result.indexOf("image") == -1)
                  {
                    this.notAnImage = true;
                    return;
                  }
                  this.images.push(event.target.result);
                  this.imagesBase64.push(event.target.result.substring(event.target.result.indexOf("base64,") + 7));
                  this.myForm.patchValue({
                     fileSource: this.images
                  });
               }
               reader.readAsDataURL(event.target.files[i]);
       }
   }
 }

 submit(){
  const val = this.myForm.value;
  this.submitValidate = false;

   if(!val.content)
    this.submitValidate = true;
   else{
      this.commentadd.content = val.content;
      this.commentadd.imageUrls = this.imagesBase64;
      this.commentadd.commentedOn = new Date();

      this.commentService.addGrade(this.commentadd).subscribe((res) => {
        console.log(res.content + " " + res.culturalOfferName + " " + res.userUsername + " " + res.imageUrls);
      })
   }
   //console.log(this.imagesBase64);
 }

}
