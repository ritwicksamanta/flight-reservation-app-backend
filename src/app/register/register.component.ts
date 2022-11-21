import { Customer } from './../util-classes/customer';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { passwordValidator } from '../shared/password.validator';
import { LoginService } from '../services/login.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  submitted:boolean=false;
  error:Customer;

  p_visible = false;//password visible
  constructor(private _fb: FormBuilder,private http:LoginService, private _router:Router) { }


  ngOnInit(): void {
    this.form = this._fb.group({
      firstName: ['',Validators.required],
      lastName: ['',Validators.required],
      address: [''],
      phoneNumber: ['', [
          Validators.pattern('^[0987]{1}[0-9]{9,10}$')
        ],
      ],
      email: ['', Validators.required],
      password: ['',Validators.required],
      confirmPassword: ['',Validators.required],

      //terms & Conditions
      tc : [false,[
        Validators.requiredTrue
      ]]
    },{validators :passwordValidator()})
  }


  renderLoginPage(){
    this._router.navigateByUrl('/login');
  }
  register(customer:Customer){

    //console.log(customer);
    this.http.register(customer).subscribe(
      async (data)=>{
        console.log(data);
        const Toast = Swal.mixin({
          toast:true,
          position: 'bottom-end',
          background:'#2b6609',
          showConfirmButton: false,
          timer: 10000,
          timerProgressBar: true,
          showCloseButton:true,


        })

        await Toast.fire({
          icon:'success',
          title:data.message,
          color:'#fff'
        }).then(
          ()=>{
            this.renderLoginPage();
          }
        );
      },
      (error) => {
        this.error = error.error;
        console.log(this.error);
        Object.keys(this.error).forEach(errorProperty => {
          const formControl = this.form.get(errorProperty);
          if(formControl){
            formControl.setErrors({
              serverError: this.error[errorProperty]
            });
          }
        });
      }
    )
  }

}
