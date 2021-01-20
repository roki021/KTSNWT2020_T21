import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OfferType } from '../../model/offer-type';

@Component({
  selector: 'app-update-offer-type',
  templateUrl: './update-offer-type.component.html',
  styleUrls: ['./update-offer-type.component.sass']
})
export class UpdateOfferTypeComponent implements OnInit {

  @Input() offer_type:OfferType;
  constructor(public activeModal:NgbActiveModal) { }

  ngOnInit(): void {
  }

  update(){
    console.log("update");
    console.log(this.offer_type.name)
    this.activeModal.close();
  }

}
