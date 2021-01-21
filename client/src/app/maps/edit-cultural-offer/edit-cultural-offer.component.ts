import { Component, Input, OnInit } from '@angular/core';

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

  constructor() {
  }

  ngOnInit(): void {
  }

  addOffer(){
    alert("Should work!");
  }

}
