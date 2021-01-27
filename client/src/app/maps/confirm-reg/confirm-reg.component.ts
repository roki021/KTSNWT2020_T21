import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RegistrationService } from '../services/registration.service';

@Component({
  selector: 'app-confirm-reg',
  templateUrl: './confirm-reg.component.html',
  styleUrls: ['./confirm-reg.component.sass']
})
export class ConfirmRegComponent implements OnInit {

  isVerified = false;
  badMessage: string;

  constructor(private regService: RegistrationService, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      const verifyToken: string = params.token;
      this.regService.confirmRegistration(verifyToken).subscribe(
        res => {
          this.isVerified = true;
        },
        error => {
          this.badMessage = error;
        }
      );
    });
  }

}
