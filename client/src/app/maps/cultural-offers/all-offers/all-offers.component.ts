import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Icons } from 'src/app/enums/icons.enum';
import { FieldDecorator } from '../../gen-table/field-decorator';
import { TableHeader } from '../../gen-table/table-header';
import { TableOperation } from '../../gen-table/table-operation';
import { CulturalOffer } from '../../model/cultural-offer';
import { CulturalOfferService } from '../../services/cultural-offer.service';

@Component({
  selector: 'app-all-offers',
  templateUrl: './all-offers.component.html',
  styleUrls: ['./all-offers.component.sass']
})
export class AllOffersComponent implements OnInit {

  @ViewChild('content', { static: false }) private content;
  @ViewChild('add_offer', {static: false}) private add_offer;
  @ViewChild('edit_offer', {static: false}) private edit_offer;

  edit_id: number;
  edit_title: string;
  edit_description: string;
  edit_images: string[];

  offers: CulturalOffer[];
  currentPage: number;
  pageSize: number;
  totalSize: number;

  tableHeader: TableHeader[] = [
    {
      headerName: 'Title',
      fieldName: ['title']
    },
    {
      headerName: 'Type',
      fieldName: ['offerType']
    },
    {
      headerName: 'Subtype',
      fieldName: ['subTypeName']
    },
    {
      headerName: 'Address',
      fieldName: ['address']
    }
  ];
  headerDecoration: FieldDecorator = {
    name: 'CulturalOffer',
    decoration: `<img src="https://upload.wikimedia.org/wikipedia/commons/{0}" class="mr-2" style="width: 20px"> {1}`
  };

  operations: TableOperation<CulturalOffer>[] = [
    {
      operation: () => this.open(this.content),
      icon: Icons.news
    },
    {
      operation: (offer: CulturalOffer) => {
        this.edit_id = offer.id;
        this.edit_title = offer.title;
        this.edit_description = offer.description;
        this.edit_images = offer.imageUrls;
        this.open(this.edit_offer);
      },
      icon: Icons.update
    },
    {
      operation: (offer: CulturalOffer) => this.removeOffer(offer.id),
      icon: Icons.remove
    }
  ];

  constructor(
    private modalService: NgbModal,
    private culturalOfferService: CulturalOfferService,
    private router: Router
    ) {
      this.currentPage=1;
      this.pageSize=6;
  }

  ngOnInit(): void {
    this.culturalOfferService.getAll().subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.culturalOfferService.getPage2(this.currentPage-1, this.pageSize).subscribe(
          res => {
            this.offers = res;
          }
        );
      }
    );
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.culturalOfferService.getAll().subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.culturalOfferService.getPage2(newPage-1, this.pageSize).subscribe(
          res => {
            this.offers = res;
          }
        );
      }
    );
	}

  saveChange(): void{
    this.ngOnInit();
  }

  addNew(): void {
    this.open(this.add_offer);
  }

  removeOffer(id){
    this.culturalOfferService.delete(id).subscribe(res => {
      this.saveChange();
    });
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg', scrollable: true});
  }

  home(){
    this.router.navigate(['']);
  }
}
