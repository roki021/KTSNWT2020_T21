import { Component, OnInit } from '@angular/core';
import { OfferType } from '../../model/offer-type';
import { OfferTypeService } from '../../services/offer-type.service';

@Component({
  selector: 'app-offer-type-list',
  templateUrl: './offer-type-list.component.html',
  styleUrls: ['./offer-type-list.component.sass']
})
export class OfferTypeListComponent implements OnInit {

  pageSize: number;
	currentPage: number;
	totalSize: number;
	offer_type_list: OfferType[];

	constructor(
		private offer_type_service: OfferTypeService
	) {
		this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;
	}

	changePage(newPage: number) {
		this.offer_type_service.getPage(newPage - 1, this.pageSize).subscribe(
			res => {
				this.offer_type_list = res.body as OfferType[];
        this.totalSize = Number(res.headers.get('Total-pages'));
			}
    );
	}

	ngOnInit() {
		this.offer_type_service.getPage(this.currentPage - 1, this.pageSize).subscribe(
			res => {
        console.log(res)
				this.offer_type_list = res.body as OfferType[];
        this.totalSize = Number(res.headers.get('Total-pages'));
        console.log(this.totalSize)
        console.log(res.headers.get('Total-pages'))
      }
      
    );
    console.log(this.totalSize)
	}

}
