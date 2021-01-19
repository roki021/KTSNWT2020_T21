import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { element } from 'protractor';
import { Icons } from 'src/app/enums/icons.enum';
import { TableHeader } from '../../gen-table/table-header';
import { TableOperation } from '../../gen-table/table-operation';
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
	offer_type_list: OfferType[] = [];
	tableHeader: TableHeader[] = [
		{
		  headerName: 'Name',
		  fieldName: ['name']
		},
		{
		  headerName: 'Subtypes Number',
		  fieldName: ['subtypesNumber']
		}
	  ];

	  operations: TableOperation<OfferType>[] = [
		{
		  operation: (element) => this.subtypesView(element.id),
		  icon: Icons.arrowRight
		},
		{
		  operation: (element) => this.update(element.id),
		  icon: Icons.update
		},
		{
		  operation: (element) => this.delete(element.id),
		  icon: Icons.remove
		}
	  ];

	constructor(
		private offer_type_service: OfferTypeService,private _router: Router,
		private route: ActivatedRoute
	) {
		this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;
	}

	changePage(newPage: number) {
		this.currentPage = newPage;
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
				this.offer_type_list = res.body as OfferType[];
				this.totalSize = Number(res.headers.get('Total-pages'));
			}

		);
	}

	addNew(){
		alert("added")
	}

	update(id){
		alert(id)
	}

	subtypesView(id){
		let path = "./" + id + "/subtypes"
		this._router.navigate([path], { relativeTo: this.route });
	}

	delete(id){
		this.offer_type_service.delete(id).subscribe(res=>{
			let page_num = this.currentPage;
			console.log(page_num)
			if(this.offer_type_list.length === 1){
			  page_num = page_num - 1;
			  console.log(page_num)
			}
			this.changePage(page_num)
		  },
		  error =>{
			alert("Error")
		  });
	}

}
