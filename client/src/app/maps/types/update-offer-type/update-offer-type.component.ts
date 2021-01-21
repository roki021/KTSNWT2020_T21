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
  @Input() offer_type:OfferType;
  not_found:boolean = false;
  bad_request:boolean = false;
  unauthorized:boolean = false;
  constructor(public activeModal:NgbActiveModal,
    private offer_type_service: OfferTypeService) { }

  ngOnInit(): void {
  }

  update(){
    this.offer_type_service.update(this.offer_type, this.offer_type.id).subscribe(
			res => {
				console.log(res);
        this.refresh();
        this.activeModal.close();
        this.bad_request = false;
        this.not_found = false;
        this.unauthorized = false;
      },
      error=>{
        console.log(error);
        if(error.status == 400){
          console.log("los req");
          this.bad_request = true;
        }
        else if(error.status == 404){
          this.not_found = true;
        }
        else if(error.status == 401 || error.status == 403){
          this.unauthorized = true;
        }
      }

		);
  }

}
