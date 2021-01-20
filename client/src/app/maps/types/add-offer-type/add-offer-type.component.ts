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
  @Input() pageNum;
  offer_type:OfferType={id:null,name:"",subtypes:[],subtypesNumber:null};
  constructor(public activeModal:NgbActiveModal,
    private offer_type_service: OfferTypeService) { 
      
    }

  ngOnInit(): void {
  }

  add(){
    //console.log(this.offer_type);
    this.offer_type_service.create(this.offer_type).subscribe(
			res => {
				console.log(res);
        this.refresh();
        this.activeModal.close();
      },
      error=>{
        console.log(error);
      }

		);
  }

}
