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
  subtype_list: Subtype[] = [];
  offer_type: string;
  delete_validation: boolean = false;
  delete_not_found_validation: boolean = false;
  unauthorized: boolean = false;
  offer_not_found: boolean = false;
  valid_offer_type: OfferType = { id: null, name: '', subtypesNumber: 0, subtypes: null };

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

  constructor(private subtypes_service: SubtypeService, private route: ActivatedRoute,
    private modalService: NgbModal, private offer_type_service: OfferTypeService) {
    this.pageSize = 2;
    this.currentPage = 1;
    this.totalSize = 1;
    this.offer_type = this.route.snapshot.paramMap.get('id');
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.subtypes_service.getPage(newPage - 1, this.pageSize, this.offer_type).subscribe(
      res => {
        this.subtype_list = res.body as Subtype[];
        this.totalSize = Number(res.headers.get('Total-pages'));
      }
    );
  }

  ngOnInit() {
    this.subtypes_service.getPage(this.currentPage - 1, this.pageSize, this.offer_type).subscribe(
      res => {
        this.subtype_list = res.body as Subtype[];
        this.totalSize = Number(res.headers.get('Total-pages'));

        if (this.subtype_list.length > 0) {
          this.valid_offer_type.name = this.subtype_list[0].offerTypeName;

        }
        else {
          this.offer_type_service.getById(this.offer_type).subscribe(
            response => {
              this.valid_offer_type = response.body as OfferType;

              this.offer_not_found = false;
            },
            error => {
              this.offer_not_found = true;
            }
          );
        }
      }
    );
  }

  addNew() {
    const modalRef = this.modalService.open(AddSubtypeComponent, { ariaLabelledBy: 'add-offer-type', size: 'lg', scrollable: true });
    modalRef.componentInstance.refresh = () => { this.changePage(this.currentPage); };
    modalRef.componentInstance.offer_type_name = this.valid_offer_type.name;
  }

  update(subtype) {
    const modalRef = this.modalService.open(UpdateSubtypeComponent, { ariaLabelledBy: 'update-offer-type', size: 'lg', scrollable: true });
    modalRef.componentInstance.subtype = subtype;
    modalRef.componentInstance.refresh = () => { this.changePage(this.currentPage); };
  }

  delete(id) {
    this.subtypes_service.delete(id).subscribe(res => {
      let page_num = this.currentPage;
      if (this.subtype_list.length === 1) {
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
