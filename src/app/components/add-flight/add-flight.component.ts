import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { FlightAddRequest } from 'src/app/util-classes/flightreq';

@Component({
  selector: 'app-add-flight',
  templateUrl: './add-flight.component.html',
  styleUrls: ['./add-flight.component.css']
})
export class AddFlightComponent implements OnInit {

  constructor(private _fb:FormBuilder,public dialogRef:MatDialogRef<AddFlightComponent>) { }
  form:FormGroup;
  /**
   * "airlineName":"Spice Jet",
    "toLocation":"Kolkata",
    "fromLocation":"Mumbai",
    "boardingTime": "08:30",
    "date":"2022-10-09",
    "totalSeat":"100"
   */


  ngOnInit(): void {
    this.form = this._fb.group({
      airlineName:['',[Validators.required]],
      fromLocation:['',[Validators.required]],
      toLocation:['',[Validators.required]],
      boardingTime:[''],
      date:[''],
      totalSeat:[''],
      gateNo:['']
    })
  }
  private flightReq:FlightAddRequest;
  addFlight(value:FlightAddRequest){
    //this.flightReq=value;
    value.date=new DatePipe('en-US').transform(value.date,'YYYY-MM-dd');
    //console.log(value);
    this.dialogRef.close(value);
  }

}
