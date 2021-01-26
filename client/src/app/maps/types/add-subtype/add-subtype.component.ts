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
  @Input() offerTypeName: string;
  badRequest = false;
  unauthorized = false;
  subtype: Subtype = { id: null, name: '', offerTypeName: null, offerNumber: null };
  constructor(public activeModal: NgbActiveModal,
              private subtypeService: SubtypeService) {

  }

  ngOnInit(): void {
  }

  add() {
    this.subtype.offerTypeName = this.offerTypeName;
    this.subtypeService.create(this.subtype).subscribe(
      res => {
        console.log(res);
        this.refresh();
        this.activeModal.close();
        this.badRequest = false;
        this.unauthorized = false;
      },
      error => {
        if (error.status === 400) {
          this.badRequest = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        }
      }

    );
  }


}
