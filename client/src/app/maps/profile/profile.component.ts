import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { error } from 'protractor';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { Guest } from '../model/guest';
import { AuthService } from '../services/auth.service';
import { ProfileService } from '../services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit {

  guest_id=1001;
  guest:Guest={id: null, username: "", emailAddress: "", firstName: "", lastName: "", password: "ignore"};
  update_bad_request: boolean = false;
  bad_request: boolean = false;
  unauthorized: boolean = false;
  not_found: boolean = false;
  constructor(private profile_service: ProfileService, private modalService: NgbModal,
    private auth_service: AuthService) { }

  ngOnInit(): void {
    this.guest_id = this.auth_service.getUserId(); 
    this.load();
  }

  load(){
    this.profile_service.getProfile(this.guest_id).subscribe(
			res => {
        this.guest = res.body as Guest;
        this.bad_request = false;
        this.not_found = false;
        this.unauthorized = false;
      },
      error => {
        if(error.status == 400){
          this.bad_request = true;
        }
        else if(error.status == 401 || error.status == 403){
          this.unauthorized = true;
        }
        else if(error.status == 404){
          this.not_found = true;
        }
      }
		);
  }

  change_password(){
    console.log(this.guest_id);
    const modalRef = this.modalService.open(ChangePasswordComponent, { ariaLabelledBy: 'add-offer-type', size: 'lg', scrollable: true });
		modalRef.componentInstance.guest_id = this.guest_id;
  }

  update(){
    this.guest.password = "ignore";
    this.profile_service.update(this.guest, this.guest_id).subscribe(
			res => {
        this.guest = res.body as Guest;
        this.update_bad_request = false;
        this.not_found = false;
        this.unauthorized = false;
        this.auth_service.refreshToken().subscribe((refreshed:boolean) => {
          if(refreshed){    
            console.log(this.auth_service.getToken());
          }
        });
      },
      error => {
        if(error.status == 400){
          this.update_bad_request = true;
        }
        else if(error.status == 401 || error.status == 403){
          this.unauthorized = true;
        }
        else if(error.status == 404){
          this.not_found = true;
        }
      }
		);
  }

}
