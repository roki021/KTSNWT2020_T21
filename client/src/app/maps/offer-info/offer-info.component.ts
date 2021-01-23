import { Component, Input, OnInit } from '@angular/core';
import { CulturalOffer } from '../model/cultural-offer';

@Component({
  selector: 'app-offer-info',
  templateUrl: './offer-info.component.html',
  styleUrls: ['./offer-info.component.sass']
})
export class OfferInfoComponent implements OnInit {

  @Input() selectedOffer: CulturalOffer;

  constructor() { }

  ngOnInit(): void {
  }

}
