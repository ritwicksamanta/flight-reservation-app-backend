import { LoginRequest } from './../util-classes/login-request';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import Swal from 'sweetalert2';
import { reduce } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private _fb: FormBuilder, private login: LoginService, private _router:Router) { }
  form: FormGroup;
  visible: boolean = false;
  ngOnInit(): void {
    //window.location.reload();
    this.form = this._fb.group({
      userName: ['', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$")]],
      password: ['', [Validators.required]]
    })
  }

  toggleVisibility() {
    this.visible = !this.visible
  }

  //new user register page

  registerNew(){
    this._router.navigate(['/register'])
  }






  doLogin(value: LoginRequest) {
    this.form.reset();//reset from
    Object.keys(this.form.controls).forEach(control => {
      const formControl = this.form.get(control);
      formControl.markAsPristine();
      formControl.markAsUntouched();

    })
    this.login.login(value).subscribe(
      (data) => {
        sessionStorage.setItem('token',data.token);
        this._router.navigate(['/dashboard']);
      },
      error => {
        console.log(error.error.error_message);
        const message = error.error.error_message ? error.error.error_message : `Please, Check your network connection
                                                                                    and try again!`;
        Swal.fire(
          {

            text: message,
            icon: 'warning',
            iconColor:'#fff',
            confirmButtonText: 'Try Again !!',
            cancelButtonText: 'Cancel',
            showCancelButton: true,
            cancelButtonColor:'#ff2d00',
            background: 'linear-gradient(120deg, rgba(0,0,0,.35), rgba(0,0,0,.75))',
            color:'#fff',
            width:'28em',
            allowOutsideClick:false,
            allowEscapeKey:false,
            showCloseButton:true,
            closeButtonAriaLabel:'Close this popup'
          }
        ).then((result) => {
          if(result.isDismissed)
            this._router.navigate([''])
        })
      }
    )
  }

}
