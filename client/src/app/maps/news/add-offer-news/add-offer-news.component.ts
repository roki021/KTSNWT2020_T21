import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { OfferNewsService } from '../../services/offer-news.service';

@Component({
  selector: 'app-add-offer-news',
  templateUrl: './add-offer-news.component.html',
  styleUrls: ['./add-offer-news.component.sass']
})
export class AddOfferNewsComponent implements OnInit {

  title: string;

  description: string;

  @Input()
  offerId: number;

  images: string[];

  @Output()
  sendEvent: EventEmitter<string>;

  constructor(private service: OfferNewsService) { 
    this.title = "";
    this.description = "";
    this.images = [];
    this.sendEvent = new EventEmitter<string>();
  }

  ngOnInit(): void {
  }

  addOffer(){
    this.service.add({
      "title": this.title,
      "description": this.description,
      "imageUrls": this.images,
      "culturalOfferId": this.offerId
    }).subscribe(res => {
      console.log(res.culturalOfferId);
      this.sendEvent.emit("OK");
    }, err => {
      alert("Some fields are empty or invalid!");
    });
  }

}
