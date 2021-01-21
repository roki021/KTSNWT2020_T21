import { Component, OnInit } from '@angular/core';

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

  constructor() { 
    this.title = "";
    this.description = "";
    this.address = "";
    this.subtype = "";
    this.images = [];
  }

  ngOnInit(): void {
  }

  addOffer(){
  }

}
