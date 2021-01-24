import { Component, Input, OnInit } from '@angular/core';
import { CulturalOffer } from '../model/cultural-offer';
import { Gallery } from 'angular-gallery';

@Component({
  selector: 'app-offer-info',
  templateUrl: './offer-info.component.html',
  styleUrls: ['./offer-info.component.sass']
})
export class OfferInfoComponent implements OnInit {

  @Input() selectedOffer: CulturalOffer;

  constructor(private gallery: Gallery) { }

  ngOnInit(): void {
  }

  showGallery(index: number) {
    const prop = {
      images: this.selectedOffer?.imageUrls.map(imageUrl => ({path: imageUrl})),
      index
    };
    this.gallery.load(prop);
  }
}
