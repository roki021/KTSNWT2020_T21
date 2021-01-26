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
  @Input() subtype: Subtype;
  notFound = false;
  badRequest = false;
  unauthorized = false;
  constructor(public activeModal: NgbActiveModal,
              private subtypeService: SubtypeService) { }

  ngOnInit(): void {
  }

  update() {
    this.subtypeService.update(this.subtype, this.subtype.id).subscribe(
      res => {
        this.refresh();
        this.activeModal.close();
        this.badRequest = false;
        this.notFound = false;
        this.unauthorized = false;
      },
      error => {
        console.log(error);
        if (error.status === 400) {
          this.badRequest = true;
        } else if (error.status === 404) {
          this.notFound = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        }
      }

    );
  }


}
