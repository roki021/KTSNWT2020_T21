import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginServiceService } from '../../services/login-service.service';
import { LoginResponse } from '../../model/login-response';
import { isNull } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.sass']
})
export class LoginFormComponent implements OnInit {

  form:FormGroup;

  constructor(private fb:FormBuilder, 
              private loginService: LoginServiceService, 
              private router: Router) { 
    this.form = this.fb.group({
      username: ['',Validators.required],
      password: ['',Validators.required]
    });
    
  }

  ngOnInit(): void {
  }

  login() {
    const val = this.form.value;

    if (val.username && val.password) {
      this.loginService.login(val.username, val.password)
          .subscribe(
              (response) => {
                    localStorage.setItem('jwt', response.accessToken);
                    console.log("jwt token: " + localStorage.getItem('jwt'));
                    this.router.navigateByUrl('/');
                  }
          ); 
    }
}
}
