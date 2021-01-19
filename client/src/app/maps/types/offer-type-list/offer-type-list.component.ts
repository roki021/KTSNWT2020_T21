import { Component, OnInit } from '@angular/core';
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
	open(content: any): void {
		throw new Error('Method not implemented.');
	}
	content(content: any): void {
		throw new Error('Method not implemented.');
	}

	pageSize: number;
	currentPage: number;
	totalSize: number;
	offer_type_list: OfferType[] = [];
	selected_offer_type_id;
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

	  operations: TableOperation[] = [
		{
		  operation: () => this.subtypesView(this.selected_offer_type_id),
		  icon: Icons.arrowRight
		},
		{
		  operation: () => this.update(),
		  icon: Icons.update
		},
		{
		  operation: () => this.delete(this.selected_offer_type_id),
		  icon: Icons.remove
		}
	  ];

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
				this.offer_type_list = res.body as OfferType[];
				this.totalSize = Number(res.headers.get('Total-pages'));
			}

		);
	}

	addNew(){
		alert("added")
	}

	update(){

	}

	subtypesView(id){

	}

	delete(id){
		alert(id);
	}

}
