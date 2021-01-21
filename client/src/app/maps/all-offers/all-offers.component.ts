import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Icons } from 'src/app/enums/icons.enum';
import { FieldDecorator } from '../gen-table/field-decorator';
import { TableHeader } from '../gen-table/table-header';
import { TableOperation } from '../gen-table/table-operation';
import { CulturalOffer } from '../model/cultural-offer';

const OFFERS: CulturalOffer[] = [
  {
    _id:1,
    title:"title 1",
    description:"desc 1",
    address:"addr 1",
    subtypeName:"sub 1",
    offerType:"type 1",
    imageUrls:[],
    longitude:11.11,
    latitude:11.11,
  },
  {
    _id:2,
    title:"title 2",
    description:"desc 2",
    address:"addr 2",
    subtypeName:"sub 2",
    offerType:"type 2",
    imageUrls:[],
    longitude:22.22,
    latitude:22.22,
  },
  {
    _id:3,
    title:"title 3",
    description:"desc 3",
    address:"addr 3",
    subtypeName:"sub 3",
    offerType:"type 3",
    imageUrls:[],
    longitude:33.33,
    latitude:33.33,
  },
  {
    _id:4,
    title:"title 4",
    description:"desc 4",
    address:"addr 4",
    subtypeName:"sub 4",
    offerType:"type 1",
    imageUrls:[],
    longitude:44.44,
    latitude:44.44,
  },
  {
    _id:5,
    title:"title 5",
    description:"desc 5",
    address:"addr 5",
    subtypeName:"sub 5",
    offerType:"type 2",
    imageUrls:[],
    longitude:55.55,
    latitude:55.55,
  }
];

interface EditOffer{
  id?: number;
  title: string;
  description: string;
  images: string[];
}

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

  offers: CulturalOffer[] = OFFERS;
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
      fieldName: ['subtypeName']
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
        this.edit_id = offer._id;
        this.edit_title = offer.title;
        this.edit_description = offer.description;
        this.edit_images = offer.imageUrls;
        this.open(this.edit_offer);
      },
      icon: Icons.update
    },
    {
      operation: () => this.open(this.content),
      icon: Icons.remove
    }
  ];

  constructor(private modalService: NgbModal) {
  }

  ngOnInit(): void {}

  addNew(): void {
    this.open(this.add_offer);
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg', scrollable: true});
  }
}
