import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { OfferNewsService } from '../../services/offer-news.service';

@Component({
  selector: 'app-edit-offer-news',
  templateUrl: './edit-offer-news.component.html',
  styleUrls: ['./edit-offer-news.component.sass']
})
export class EditOfferNewsComponent implements OnInit {

  @Input()
  id: number;

  @Input()
  title: string;

  @Input()
  description: string;

  @Input()
  images: string[];

  @Output()
  sendEvent: EventEmitter<string>;

  constructor(private service: OfferNewsService) {
    this.sendEvent = new EventEmitter<string>();
  }

  ngOnInit(): void {
  }

  addOffer(){
    this.service.update(this.id, {
      "title": this.title,
      "description": this.description,
      "imageUrls": this.images
    }).subscribe(res => {
      console.log(res.culturalOfferId);
      this.sendEvent.emit("OK");
    }, err => {
      alert("Some fields are empty or invalid!");
    });
  }

}
