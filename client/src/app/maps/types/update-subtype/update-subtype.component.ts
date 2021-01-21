import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Subtype } from '../../model/subtype';
import { SubtypeService } from '../../services/subtype.service';

@Component({
  selector: 'app-update-subtype',
  templateUrl: './update-subtype.component.html',
  styleUrls: ['./update-subtype.component.sass']
})
export class UpdateSubtypeComponent implements OnInit {

  @Input() refresh;
  @Input() subtype:Subtype;
  not_found:boolean = false;
  bad_request:boolean = false;
  unauthorized:boolean = false;
  constructor(public activeModal:NgbActiveModal,
    private subtype_service: SubtypeService) { }

  ngOnInit(): void {
  }

  update(){
    this.subtype_service.update(this.subtype, this.subtype.id).subscribe(
			res => {
				console.log(res);
        this.refresh();
        this.activeModal.close();
        this.bad_request = false;
        this.not_found = false;
        this.unauthorized = false;
      },
      error=>{
        console.log(error);
        if(error.status == 400){
          console.log("los req");
          this.bad_request = true;
        }
        else if(error.status == 404){
          this.not_found = true;
        }
        else if(error.status == 401){
          this.unauthorized = true;
        }
      }

		);
  }


}
