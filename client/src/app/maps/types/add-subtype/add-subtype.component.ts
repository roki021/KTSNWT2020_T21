import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Subtype } from '../../model/subtype';
import { SubtypeService } from '../../services/subtype.service';

@Component({
  selector: 'app-add-subtype',
  templateUrl: './add-subtype.component.html',
  styleUrls: ['./add-subtype.component.sass']
})
export class AddSubtypeComponent implements OnInit {

  @Input() refresh;
  @Input() offer_type_name: string;
  bad_request: boolean = false;
  unauthorized: boolean = false;
  subtype: Subtype = { id: null, name: '', offerTypeName: null, offerNumber: null };
  constructor(public activeModal: NgbActiveModal,
    private subtype_service: SubtypeService) {

  }

  ngOnInit(): void {
  }

  add() {
    this.subtype.offerTypeName = this.offer_type_name;
    this.subtype_service.create(this.subtype).subscribe(
      res => {
        console.log(res);
        this.refresh();
        this.activeModal.close();
        this.bad_request = false;
        this.unauthorized = false;
      },
      error => {
        if (error.status == 400) {
          this.bad_request = true;
        }
        else if (error.status == 401 || error.status == 403) {
          this.unauthorized = true;
        }
      }

    );
  }


}
