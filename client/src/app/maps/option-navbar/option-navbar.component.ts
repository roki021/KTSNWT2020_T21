import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-option-navbar',
  templateUrl: './option-navbar.component.html',
  styleUrls: ['./option-navbar.component.sass']
})
export class OptionNavbarComponent implements OnInit {

  authority: string;

  constructor(private authService: AuthService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.authority = this.authService.getRole();
  }

  logOut(): void {
    this.authService.logout();
    location.reload();
  }

  changePassword(){
    console.log(this.authService.getUserId());
    const modalRef = this.modalService.open(ChangePasswordComponent, { ariaLabelledBy: 'add-offer-type', size: 'lg', scrollable: true });
		modalRef.componentInstance.guest_id = this.authService.getUserId();
  }
}
