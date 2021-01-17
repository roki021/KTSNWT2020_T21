import { Component, OnInit } from '@angular/core';
import { Subtype } from '../../model/subtype';
import { SubtypeService } from '../../services/subtype.service';

@Component({
  selector: 'app-subtype-list',
  templateUrl: './subtype-list.component.html',
  styleUrls: ['./subtype-list.component.sass']
})
export class SubtypeListComponent implements OnInit {

  pageSize: number;
  currentPage: number;
  totalSize: number;
  subtype_list: Subtype[];
  offer_type: string;

  constructor(private subtypes_service: SubtypeService) {
    this.pageSize = 2;
    this.currentPage = 1;
    this.totalSize = 1;
    this.offer_type = "2";
  }

  changePage(newPage: number) {
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
        console.log(res);
			}
		);
	}

}
