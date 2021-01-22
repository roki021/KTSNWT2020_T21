import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-option-navbar',
  templateUrl: './option-navbar.component.html',
  styleUrls: ['./option-navbar.component.sass']
})
export class OptionNavbarComponent implements OnInit {

  authority: string;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authority = this.authService.getRole();
  }

  logOut(): void {
    this.authService.logout();
    location.reload();
  }
}
