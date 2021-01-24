import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Icons } from 'src/app/enums/icons.enum';
import { FieldDecorator } from '../../gen-table/field-decorator';
import { TableHeader } from '../../gen-table/table-header';
import { TableOperation } from '../../gen-table/table-operation';
import { OfferNews } from '../../model/offer-news';
import { OfferNewsService } from '../../services/offer-news.service';

@Component({
  selector: 'app-all-offer-news',
  templateUrl: './all-offer-news.component.html',
  styleUrls: ['./all-offer-news.component.sass']
})
export class AllOfferNewsComponent implements OnInit {

  @ViewChild('content', { static: false }) private content;
  @ViewChild('add_news', {static: false}) private add_news;
  @ViewChild('edit_news', {static: false}) private edit_news;

  @Input()
  offerId: number;

  edit_id: number;
  edit_title: string;
  edit_description: string;
  edit_images: string[];

  news: OfferNews[];
  currentPage: number;
  pageSize: number;
  totalSize: number;

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
      operation: (news: OfferNews) => {
        this.edit_id = news.id;
        this.edit_title = news.title;
        this.edit_description = news.description;
        this.edit_images = news.imageUrls;
        this.open(this.edit_news);
      },
      icon: Icons.update
    },
    {
      operation: (news: OfferNews) => this.removeOffer(news.id),
      icon: Icons.remove
    },
    {
      operation: () => this.open(this.content),
      icon: Icons.preview
    }
  ];

  constructor(
    private modalService: NgbModal,
    private offerNewsService: OfferNewsService,
    private router: Router,
    private route: ActivatedRoute
    ) {
      this.currentPage=1;
      this.pageSize=6;
      this.offerId = Number(this.route.snapshot.paramMap.get('offer_id'));
  }

  ngOnInit(): void {
    this.offerNewsService.getAll(this.offerId).subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.offerNewsService.getPage(this.offerId, this.currentPage-1, this.pageSize).subscribe(
          res => {
            this.news = res;
          }
        );
      }
    );
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.offerNewsService.getAll(this.offerId).subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.offerNewsService.getPage(this.offerId, newPage-1, this.pageSize).subscribe(
          res => {
            this.news = res;
          }
        );
      }
    );
	}

  saveChange(): void{
    this.ngOnInit();
  }

  addNew(): void {
    this.open(this.add_news);
  }

  removeOffer(id){
    this.offerNewsService.delete(id).subscribe(res => {
      this.saveChange();
    });
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg', scrollable: true});
  }

  home(){
    this.router.navigate(['']);
  }

  back(){
    this.router.navigate(['/all_offers']);
  }

}
