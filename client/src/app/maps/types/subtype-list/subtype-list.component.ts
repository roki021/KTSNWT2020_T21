import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Icons } from 'src/app/enums/icons.enum';
import { TableHeader } from '../../gen-table/table-header';
import { TableOperation } from '../../gen-table/table-operation';
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
  subtype_list: Subtype[] = [];
  offer_type: string;
  delete_validation:boolean = false;
	delete_not_found_validation:boolean = false;
	unauthorized:boolean = false;

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
        operation: (element) => this.update(element.id),
        icon: Icons.update
      },
      {
        operation: (element) => this.delete(element.id),
        icon: Icons.remove
      }
      ];

  constructor(private subtypes_service: SubtypeService, private route: ActivatedRoute) {
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
        console.log(res);
			}
		);
  }
  
  addNew(){
    alert("ADDED")
  }

  update(id){

  }

  delete(id){
    this.subtypes_service.delete(id).subscribe(res=>{
      let page_num = this.currentPage;
      console.log(page_num)
      if(this.subtype_list.length === 1){
        page_num = page_num - 1;
        console.log(page_num)
      }
      this.changePage(page_num)
      this.delete_validation = false;
			this.delete_not_found_validation = false;
			this.unauthorized = false;
    },
    error =>{
      console.log(error.status)
			if(error.status == 400){
				this.delete_validation = true;
			}
			else if(error.status == 404){
				this.delete_not_found_validation = true;
			}
			else if(error.status == 401){
				this.unauthorized = true;
			}
    });
  }

}
