import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CulturalOfferService } from '../services/cultural-offer.service';

@Component({
  selector: 'app-edit-cultural-offer',
  templateUrl: './edit-cultural-offer.component.html',
  styleUrls: ['./edit-cultural-offer.component.sass']
})
export class EditCulturalOfferComponent implements OnInit {

  @Input('id')
  id: number;

  @Input('title')
  title: string;

  @Input('description')
  description: string;

  @Input('images')
  images: string[];

  @Output()
  sendEvent: EventEmitter<string>;

  constructor(private service: CulturalOfferService) {
    this.sendEvent = new EventEmitter<string>();
  }

  ngOnInit(): void {
  }

  addOffer(){
    this.service.update(this.id, {
      "title": this.title,
      "description": this.description,
      "imageUrls": this.images,
    }).subscribe(res => {
      console.log(res.id);
      this.sendEvent.emit("OK");
    }, err => {
      alert("Some changed fields are not valid!");
    })
  }

}
