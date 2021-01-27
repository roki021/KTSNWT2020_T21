import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { OfferNewsService } from '../../services/offer-news.service';

@Component({
  selector: 'app-edit-offer-news',
  templateUrl: './edit-offer-news.component.html',
  styleUrls: ['./edit-offer-news.component.sass']
})
export class EditOfferNewsComponent implements OnInit {

  @Input('id')
  id: number;

  title: string;

  description: string;

  images: string[];

  base64s: string[];

  @Output()
  sendEvent: EventEmitter<string>;

  constructor(private service: OfferNewsService) {
    this.sendEvent = new EventEmitter<string>();
  }

  ngOnInit(): void {
    this.service.getOne(this.id).subscribe(res => {
      this.title = res.title;
      this.description = res.description;
      this.images = res.imageUrls;
      this.base64s = [];
    });
  }

  addImage(event){
    let reader = new FileReader();
    reader.onload = (event:any) => {
      console.log(event.target.result.substring(event.target.result.indexOf("base64,") + 7));
      this.images.push(event.target.result);
      this.base64s.push(event.target.result.substring(event.target.result.indexOf("base64,") + 7));
    }
    reader.readAsDataURL(event.target.files[0]);
    for (let img of this.images)
      console.log(img);
  }

  addOffer(){
    this.service.update(this.id, {
      "title": this.title,
      "description": this.description,
      "imageUrls": this.base64s
    }).subscribe(res => {
      console.log(res.culturalOfferId);
      alert("Offer news updated!");
      this.sendEvent.emit("OK");
    }, err => {
      alert("Some fields are empty or invalid!");
    });
  }

}
