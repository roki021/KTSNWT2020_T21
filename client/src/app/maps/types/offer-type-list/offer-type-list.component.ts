import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { faMapMarkedAlt } from '@fortawesome/free-solid-svg-icons';
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
  faMarkedMap = faMapMarkedAlt;
  offerTypeList: OfferType[] = [];
  deleteValidation = false;
  deleteNotFoundValidation = false;
  unauthorized = false;
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
    private offerTypeService: OfferTypeService, private router: Router,
    private route: ActivatedRoute, private modalService: NgbModal
  ) {
    this.pageSize = 2;
    this.currentPage = 1;
    this.totalSize = 1;
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.offerTypeService.getPage(newPage - 1, this.pageSize).subscribe(
      res => {
        this.offerTypeList = res.body as OfferType[];
        this.totalSize = Number(res.headers.get('Total-pages'));
      }
    );
  }

  ngOnInit() {
    this.offerTypeService.getPage(this.currentPage - 1, this.pageSize).subscribe(
      res => {
        this.offerTypeList = res.body as OfferType[];
        this.totalSize = Number(res.headers.get('Total-pages'));
      }

    );
  }

  addNew() {
    const modalRef = this.modalService.open(AddOfferTypeComponent, { ariaLabelledBy: 'add-offer-type', size: 'lg', scrollable: true });
    modalRef.componentInstance.refresh = () => { this.changePage(this.currentPage); };
  }

  update(offerType) {
    const modalRef = this.modalService.open(UpdateOfferTypeComponent,
       { ariaLabelledBy: 'update-offer-type', size: 'lg', scrollable: true });
    modalRef.componentInstance.offerType = offerType;
    modalRef.componentInstance.refresh = () => { this.changePage(this.currentPage); };
  }

  subtypesView(id) {
    const path = './' + id + '/subtypes';
    this.router.navigate([path], { relativeTo: this.route });
  }

  delete(id) {
    this.offerTypeService.delete(id).subscribe(res => {
      let pageNum = this.currentPage;
      if (this.offerTypeList.length === 1) {
        pageNum = pageNum - 1;
      }
      this.changePage(pageNum);
      this.deleteValidation = false;
      this.deleteNotFoundValidation = false;
      this.unauthorized = false;
    },
      error => {
        if (error.status === 400) {
          this.deleteValidation = true;
        } else if (error.status === 404) {
          this.deleteNotFoundValidation = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        }
      });
  }

}
