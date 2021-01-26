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

  password: Password = { oldPassword: '', newPassword: '', repetedPassword: '' };
  @Input() guestId: number;
  badRequest = false;
  unauthorized = false;
  notFound = false;
  badRepeat = false;
  constructor(public activeModal: NgbActiveModal,
              private profileService: ProfileService) { }

  ngOnInit(): void {
  }

  change() {
    if (this.password.repetedPassword === this.password.newPassword && this.password.repetedPassword !== '') {
      this.badRepeat = false;
      this.profileService.changePassword(this.password, this.guestId).subscribe(
        res => {
          this.activeModal.close();
        },
        error => {
          if (error.status === 400) {
            this.badRequest = true;
          } else if (error.status === 401 || error.status === 403) {
            this.unauthorized = true;
          } else if (error.status === 404) {
            this.notFound = true;
          }
        }
      );
    } else {
      this.badRepeat = true;
    }
  }

}
