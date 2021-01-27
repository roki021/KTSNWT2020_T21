import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { RegistrationService } from '../services/registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.sass']
})
export class RegistrationComponent implements OnInit {

  form:FormGroup;
  public wrongUsernameOrPass:boolean;
  public errorMessage = "";
  public emptyField = false;
  public passwordmissmatch = false;
  public invalidemail = false;

  constructor(private fb:FormBuilder, 
              private registrationService: RegistrationService, 
              private router: Router) { 
    this.form = this.fb.group({
      firstname: ['',Validators.required],
      lastname: ['',Validators.required],
      email: ['',Validators.email],
      username: ['',Validators.required],
      password: ['',Validators.required],
      repeatpassword: ['', Validators.required]
    });
    
  }

  ngOnInit(): void {
  }

  register(){
    const val = this.form.value;
    this.emptyField = false;
    this.passwordmissmatch = false;
    this.invalidemail = false;
    var re = new RegExp("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    if (!val.username || !val.password || !val.firstname || !val.lastname || !val.repeatpassword || !val.email){
      this.emptyField = true;
    }
    else if(!(val.password === val.repeatpassword)){
      this.passwordmissmatch = true;
    }
    else if(!re.test(val.email)){
     this.invalidemail = true; 
    }
    else{
      this.registrationService.register(val.username, val.password, val.firstname, val.lastname, val.email)
      .subscribe(
        (loggedIn:boolean) => {
            if(loggedIn){    
              //console.log(this.loginService.getToken());
              this.router.navigateByUrl('/postregistration');
              //console.log("Uspesno registrovan")
            } 
          },
        (err:Error) => {
          if(err.toString()==='Username or email already in use'){
            this.wrongUsernameOrPass = true;
            console.log(err);
          }
          else{
            throwError(err);
          }
        }
      ); 
    }
  }

}
