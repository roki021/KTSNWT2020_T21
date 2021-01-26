import { Component, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { Icons } from 'src/app/enums/icons.enum';
import { FieldDecorator } from '../gen-table/field-decorator';
import { TableHeader } from '../gen-table/table-header';
import { TableOperation } from '../gen-table/table-operation';
import { CulturalOffer } from '../model/cultural-offer';
import { OfferNews } from '../model/offer-news';
import { OfferNewsService } from '../services/offer-news.service';

@Component({
  selector: 'app-offer-news-view',
  templateUrl: './offer-news-view.component.html',
  styleUrls: ['./offer-news-view.component.sass']
})
export class OfferNewsViewComponent implements OnInit {

  @ViewChild('thenBlock', {static: false}) public thenBlock;
  @ViewChild('elseBlock', {static: false}) public elseBlock;

  news: OfferNews[];
  currentPage: number;
  pageSize: number;
  totalSize: number;
  allOffers: boolean;

  view_title: string;
  view_desc: string;
  view_images: string[];

  @Input()
  offer: CulturalOffer;

  tableHeader: TableHeader[] = [
    {
      headerName: 'Title',
      fieldName: ['title']
    },
    {
      headerName: 'Publish Date',
      fieldName: ['date']
    }
  ];
  headerDecoration: FieldDecorator = {
    name: 'CulturalOffer',
    decoration: `<img src="https://upload.wikimedia.org/wikipedia/commons/{0}" class="mr-2" style="width: 20px"> {1}`
  };

  operations: TableOperation<OfferNews>[] = [
    {
      operation: (news: OfferNews) => this.open(news.title, news.description, news.imageUrls),
      icon: Icons.preview
    }
  ];

  constructor(
    private offerNewsService: OfferNewsService
    ) {
      this.currentPage=1;
      this.pageSize=3;
  }

  ngOnInit(): void {
    console.log(this.allOffers);
    this.allOffers=true;
    this.offerNewsService.getAll(this.offer.id).subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.offerNewsService.getPage(this.offer.id, this.currentPage-1, this.pageSize).subscribe(
          res => {
            this.news = res;
          }
        );
      }
    );
  }

  ngOnChanges(changes: SimpleChanges){
    if (changes.offer){
      this.ngOnInit();
    }
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.offerNewsService.getAll(this.offer.id).subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.offerNewsService.getPage(this.offer.id, newPage-1, this.pageSize).subscribe(
          res => {
            this.news = res;
          }
        );
      }
    );
  }
  
  open(title: string, description: string, images: string[]): void {
    this.view_title = title;
    this.view_desc = description;
    this.view_images = images;
    this.allOffers = false;
  }

  back() {
    this.allOffers = true;
  }

}
