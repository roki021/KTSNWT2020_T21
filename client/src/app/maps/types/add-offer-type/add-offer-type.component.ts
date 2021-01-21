import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OfferTypeListComponent } from '../offer-type-list/offer-type-list.component';
import { OfferType } from '../../model/offer-type';
import { OfferTypeService } from '../../services/offer-type.service';
import { FormGroup } from '@angular/forms';
import { error } from 'protractor';

@Component({
  selector: 'app-add-offer-type',
  templateUrl: './add-offer-type.component.html',
  styleUrls: ['./add-offer-type.component.sass']
})
export class AddOfferTypeComponent implements OnInit {

  @Input() refresh;
  bad_request: boolean = false;
  unauthorized: boolean = false;
  repeated_subtype: boolean = false;
  offer_type: OfferType = { id: null, name: '', subtypes: [], subtypesNumber: null };
  subtypes = [];
  subtype: string = '';
  constructor(public activeModal: NgbActiveModal,
    private offer_type_service: OfferTypeService) {

  }

  ngOnInit(): void {
  }

  addSubtype() {
    if (this.subtypes.indexOf(this.subtype) == -1) {
      this.subtypes.push(this.subtype);
      this.subtype = '';
      this.repeated_subtype = false;
    }
    else {
      this.repeated_subtype = true;
    }
  }

  remove(index) {
    this.subtypes.splice(index, 1);
    this.repeated_subtype = false;
  }

  add() {
    this.repeated_subtype = false;
    this.offer_type.subtypes = this.subtypes;
    this.offer_type_service.create(this.offer_type).subscribe(
      res => {
        this.refresh();
        this.activeModal.close();
        this.bad_request = false;
        this.unauthorized = false;
      },
      error => {
        if (error.status == 400) {
          this.bad_request = true;
        }
        else if (error.status == 401 || error.status == 403) {
          this.unauthorized = true;
        }
      }

    );
  }

}
