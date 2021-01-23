import { Component, Input, OnInit } from '@angular/core';
import { NgbRatingConfig } from '@ng-bootstrap/ng-bootstrap';
import { CulturalOffer } from '../model/cultural-offer';

@Component({
  selector: 'app-offer-view',
  templateUrl: './offer-view.component.html',
  styleUrls: ['./offer-view.component.sass'],
  providers: [NgbRatingConfig]
})
export class OfferViewComponent implements OnInit {

  @Input() selectedOffer: CulturalOffer;
  btnSubType: string = 'btn-primary';
  btnSubText: string = 'Subscribe';
  overallGrade: number = 2.5;
  active: number = 1;

  constructor(config: NgbRatingConfig) {
    config.max = 5;
    config.readonly = true;
  }

  ngOnInit(): void {
  }

}
