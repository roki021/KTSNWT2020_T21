import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OfferType } from '../../model/offer-type';
import { OfferTypeService } from '../../services/offer-type.service';

@Component({
  selector: 'app-update-offer-type',
  templateUrl: './update-offer-type.component.html',
  styleUrls: ['./update-offer-type.component.sass']
})
export class UpdateOfferTypeComponent implements OnInit {

  @Input() refresh;
  @Input() offerType: OfferType;
  notFound = false;
  badRequest = false;
  unauthorized = false;
  constructor(public activeModal: NgbActiveModal,
              private offerTypeService: OfferTypeService) { }

  ngOnInit(): void {
  }

  update() {
    this.offerTypeService.update(this.offerType, this.offerType.id).subscribe(
      res => {
        this.refresh();
        this.activeModal.close();
        this.badRequest = false;
        this.notFound = false;
        this.unauthorized = false;
      },
      error => {
        if (error.status === 400) {
          this.badRequest = true;
        } else if (error.status === 404) {
          this.notFound = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        }
      }

    );
  }

}
