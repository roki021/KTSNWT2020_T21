import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { faMapMarkedAlt } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Icons } from 'src/app/enums/icons.enum';
import { TableHeader } from '../../gen-table/table-header';
import { TableOperation } from '../../gen-table/table-operation';
import { OfferType } from '../../model/offer-type';
import { Subtype } from '../../model/subtype';
import { OfferTypeService } from '../../services/offer-type.service';
import { SubtypeService } from '../../services/subtype.service';
import { AddSubtypeComponent } from '../add-subtype/add-subtype.component';
import { UpdateSubtypeComponent } from '../update-subtype/update-subtype.component';

@Component({
  selector: 'app-subtype-list',
  templateUrl: './subtype-list.component.html',
  styleUrls: ['./subtype-list.component.sass']
})
export class SubtypeListComponent implements OnInit {

  pageSize: number;
  currentPage: number;
  totalSize: number;
  faMarkedMap = faMapMarkedAlt;
  subtypeList: Subtype[] = [];
  offerType: string;
  deleteValidation = false;
  deleteNotFoundValidation = false;
  unauthorized = false;
  offerNotFound = false;
  validOfferType: OfferType = { id: null, name: '', subtypesNumber: 0, subtypes: null };

  tableHeader: TableHeader[] = [
    {
      headerName: 'Name',
      fieldName: ['name']
    },
    {
      headerName: 'Number Of Offers',
      fieldName: ['offerNumber']
    }
  ];

  operations: TableOperation<Subtype>[] = [
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
    private subtypesService: SubtypeService, private route: ActivatedRoute,
    private modalService: NgbModal, private offerTypeService: OfferTypeService) {
    this.pageSize = 2;
    this.currentPage = 1;
    this.totalSize = 1;
    this.offerType = this.route.snapshot.paramMap.get('id');
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.subtypesService.getPage(newPage - 1, this.pageSize, this.offerType).subscribe(
      res => {
        this.subtypeList = res.body as Subtype[];
        this.totalSize = Number(res.headers.get('Total-pages'));
      }
    );
  }

  ngOnInit() {
    this.subtypesService.getPage(this.currentPage - 1, this.pageSize, this.offerType).subscribe(
      res => {
        this.subtypeList = res.body as Subtype[];
        this.totalSize = Number(res.headers.get('Total-pages'));

        if (this.subtypeList.length > 0) {
          this.validOfferType.name = this.subtypeList[0].offerTypeName;

        } else {
          this.offerTypeService.getById(this.offerType).subscribe(
            response => {
              this.validOfferType = response.body as OfferType;

              this.offerNotFound = false;
            },
            error => {
              this.offerNotFound = true;
            }
          );
        }
      }
    );
  }

  addNew() {
    const modalRef = this.modalService.open(AddSubtypeComponent, { ariaLabelledBy: 'add-subtype', size: 'lg', scrollable: true });
    modalRef.componentInstance.refresh = () => { this.changePage(this.currentPage); };
    modalRef.componentInstance.offerTypeName = this.validOfferType.name;
  }

  update(subtype) {
    const modalRef = this.modalService.open(UpdateSubtypeComponent, { ariaLabelledBy: 'update-subtype', size: 'lg', scrollable: true });
    modalRef.componentInstance.subtype = subtype;
    modalRef.componentInstance.refresh = () => { this.changePage(this.currentPage); };
  }

  delete(id) {
    this.subtypesService.delete(id).subscribe(res => {
      let pageNum = this.currentPage;
      if (this.subtypeList.length === 1) {
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
