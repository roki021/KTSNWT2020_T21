import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { LoginResponse } from '../../model/login-response';
import { isNull } from '@angular/compiler/src/output/output_ast';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.sass']
})
export class LoginFormComponent implements OnInit {

  form:FormGroup;
  public wrongUsernameOrPass:boolean;
  public errorMessage = "";
  public invalidData = false;

  constructor(private fb:FormBuilder, 
              private loginService: AuthService, 
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
    this.wrongUsernameOrPass = false;

    if (val.username && val.password) {
      this.invalidData = false;
      this.loginService.login(val.username, val.password)
          .subscribe(
              (loggedIn:boolean) => {
                  if(loggedIn){    
                    this.router.navigateByUrl('/');
                  }
                  
                },
              (err:Error) => {
                if(err.toString()==='Ilegal login'){
                  this.wrongUsernameOrPass = true;
                  console.log(err);
                }
                else{
                  throwError(err);
                }
              }
          ); 
    }
    else{
      this.invalidData = true;
      this.errorMessage = "Both username and password are required";
    }
}
}
