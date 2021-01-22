import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Password } from '../model/password';
import { ProfileService } from '../services/profile.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.sass']
})
export class ChangePasswordComponent implements OnInit {

  password: Password = { oldPassword: "", newPassword: "", repetedPassword: "" };
  @Input() guest_id: number;
  bad_request: boolean = false;
  unauthorized: boolean = false;
  not_found: boolean = false;
  bad_repeat: boolean = false;
  constructor(public activeModal: NgbActiveModal,
    private profile_service: ProfileService) { }

  ngOnInit(): void {
  }

  change() {
    if (this.password.repetedPassword === this.password.newPassword && this.password.repetedPassword != "") {
      this.bad_repeat = false;
      this.profile_service.changePassword(this.password, this.guest_id).subscribe(
        res => {
          this.activeModal.close();
        },
        error => {
          if (error.status == 400) {
            this.bad_request = true;
          }
          else if (error.status == 401 || error.status == 403) {
            this.unauthorized = true;
          }
          else if (error.status == 404) {
            this.not_found = true;
          }
        }
      )
    }
    else{
      this.bad_repeat = true;
    }
  }

}
