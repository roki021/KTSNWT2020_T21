import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CulturalOfferService } from '../../services/cultural-offer.service';

@Component({
  selector: 'app-add-cultural-offer',
  templateUrl: './add-cultural-offer.component.html',
  styleUrls: ['./add-cultural-offer.component.sass']
})
export class AddCulturalOfferComponent implements OnInit {

  title: string;

  description: string;

  address: string;

  subtype: string;

  images: string[];

  @Output()
  sendEvent: EventEmitter<string>;

  constructor(private service: CulturalOfferService) { 
    this.title = "";
    this.description = "";
    this.address = "";
    this.subtype = "";
    this.images = [];
    this.sendEvent = new EventEmitter<string>();
  }

  ngOnInit(): void {
  }

  addOffer(){
    this.service.getLocationDetails(this.address).subscribe(res => {
      if (res.length > 0){
        this.service.add({
          "title": this.title,
          "description": this.description,
          "address": this.address,
          "subTypeName": this.subtype.split(" ", 1)[0].toLowerCase(),
          "imageUrls": this.images,
          "latitude": res[0].lat,
          "longitude": res[0].lon
        }).subscribe(res => {
          console.log("New offer id: "+res.id);
          this.sendEvent.emit("OK");
        }, err => {
          alert("Some fields are empty or invalid!");
        });
      }
      else{
        alert("Address given is not valid!");
      }
    });
  }

}
