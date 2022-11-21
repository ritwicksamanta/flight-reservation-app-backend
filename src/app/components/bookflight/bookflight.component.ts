import { User } from './../../util-classes/user';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import { Flight } from 'src/app/util-classes/Flight';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
import { FlightBookRequest } from 'src/app/util-classes/FlightBookreq';

@Component({
  selector: 'app-bookflight',
  templateUrl: './bookflight.component.html',
  styleUrls: ['./bookflight.component.css']
})
export class BookflightComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) readonly data:Flight,private _fb:FormBuilder,private _login:LoginService,private _dialogRef:MatDialogRef<BookflightComponent>) {}
  form:FormGroup;
  user_id!:string;
  ngOnInit(): void {


    this.getUserName();
    this.form = this._fb.group({
      airlineName:[`${this.data.airlineName}`],
      source:[`${this.data.fromLocation}`],
      destination:[`${this.data.toLocation}`],
      date:[`${this.data.date}`],
      boardingTime:[`${this.data.boardingTime}`],
      flightId:[`${this.data.flightId}`],

      seatNo:['',[Validators.required]]
    })
  }

  getUserName(){
    this._login.getUser().subscribe(data=>{
      this.user_id=(<User>data).email;
      console.log(this.user_id)
    })
    // console.log(this.user_id);
    // return this.user_id;
  }

  bookFlight(value){
    console.clear();
    // console.log(value,this.user_id);
    let bookingRequest:FlightBookRequest = new FlightBookRequest();
    bookingRequest.customerId=this.user_id;
    bookingRequest.flightId=value.flightId;
    bookingRequest.seatNo=value.seatNo;
    console.log('Booking Request Object',bookingRequest);
    this._dialogRef.close(bookingRequest);
  }
}
