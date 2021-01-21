import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Icons } from 'src/app/enums/icons.enum';
import { TableHeader } from '../../gen-table/table-header';
import { TableOperation } from '../../gen-table/table-operation';
import { OfferType } from '../../model/offer-type';
import { OfferTypeService } from '../../services/offer-type.service';
import { AddOfferTypeComponent } from '../add-offer-type/add-offer-type.component';
import { UpdateOfferTypeComponent } from '../update-offer-type/update-offer-type.component';

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
	delete_validation: boolean = false;
	delete_not_found_validation: boolean = false;
	unauthorized: boolean = false;
	addContent: AddOfferTypeComponent;
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
			operation: (element) => this.update(element),
			icon: Icons.update
		},
		{
			operation: (element) => this.delete(element.id),
			icon: Icons.remove
		}
	];

	constructor(
		private offer_type_service: OfferTypeService, private _router: Router,
		private route: ActivatedRoute, private modalService: NgbModal
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

	addNew() {
		const modalRef = this.modalService.open(AddOfferTypeComponent, { ariaLabelledBy: 'add-offer-type', size: 'lg', scrollable: true });
		modalRef.componentInstance.refresh = () => { this.changePage(this.currentPage); };
	}

	update(offer_type) {
		const modalRef = this.modalService.open(UpdateOfferTypeComponent, { ariaLabelledBy: 'update-offer-type', size: 'lg', scrollable: true });
		modalRef.componentInstance.offer_type = offer_type;
		modalRef.componentInstance.refresh = () => { this.changePage(this.currentPage); };
	}

	subtypesView(id) {
		const path = './' + id + '/subtypes';
		this._router.navigate([path], { relativeTo: this.route });
	}

	delete(id) {
		this.offer_type_service.delete(id).subscribe(res => {
			let page_num = this.currentPage;
			if (this.offer_type_list.length === 1) {
				page_num = page_num - 1;
			}
			this.changePage(page_num);
			this.delete_validation = false;
			this.delete_not_found_validation = false;
			this.unauthorized = false;
		},
			error => {
				if (error.status == 400) {
					this.delete_validation = true;
				}
				else if (error.status == 404) {
					this.delete_not_found_validation = true;
				}
				else if (error.status == 401 || error.status == 403) {
					this.unauthorized = true;
				}
			});
	}

}
