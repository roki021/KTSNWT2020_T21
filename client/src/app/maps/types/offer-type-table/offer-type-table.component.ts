import { Component, Input, OnInit } from '@angular/core';
import { OfferType } from '../../model/offer-type';

@Component({
  selector: 'app-offer-type-table',
  templateUrl: './offer-type-table.component.html',
  styleUrls: ['./offer-type-table.component.sass']
})
export class OfferTypeTableComponent implements OnInit {

  @Input() offer_types:OfferType[];

  constructor() { }

  ngOnInit(): void {
  }

}
