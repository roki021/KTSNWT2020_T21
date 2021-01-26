import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OfferTypeListComponent } from '../offer-type-list/offer-type-list.component';
import { OfferType } from '../../model/offer-type';
import { OfferTypeService } from '../../services/offer-type.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-offer-type',
  templateUrl: './add-offer-type.component.html',
  styleUrls: ['./add-offer-type.component.sass']
})
export class AddOfferTypeComponent implements OnInit {

  @Input() refresh;
  badRequest = false;
  unauthorized = false;
  repeatedSubtype = false;
  offerType: OfferType = { id: null, name: '', subtypes: [], subtypesNumber: null };
  subtypes = [];
  subtype = '';
  constructor(public activeModal: NgbActiveModal,
              private offerTypeService: OfferTypeService) {

  }

  ngOnInit(): void {
  }

  addSubtype() {
    if (this.subtypes.indexOf(this.subtype) === -1) {
      this.subtypes.push(this.subtype);
      this.subtype = '';
      this.repeatedSubtype = false;
    } else {
      this.repeatedSubtype = true;
    }
  }

  remove(index) {
    this.subtypes.splice(index, 1);
    this.repeatedSubtype = false;
  }

  add() {
    this.repeatedSubtype = false;
    this.offerType.subtypes = this.subtypes;
    this.offerTypeService.create(this.offerType).subscribe(
      res => {
        this.refresh();
        this.activeModal.close();
        this.badRequest = false;
        this.unauthorized = false;
      },
      error => {
        if (error.status === 400) {
          this.badRequest = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        }
      }

    );
  }

}
