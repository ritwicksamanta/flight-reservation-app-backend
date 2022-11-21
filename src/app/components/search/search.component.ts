import { MatTableDataSource } from '@angular/material/table';
import { FlightServiceService } from './../../services/flight-service.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { dateValidator } from 'src/app/shared/date.validator';
import { DatePipe } from '@angular/common';
import { Flight } from 'src/app/util-classes/Flight';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { BookflightComponent } from '../bookflight/bookflight.component';
import { BookingService } from 'src/app/services/booking.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  form:FormGroup;
  showTable:boolean;
  constructor(private _fb:FormBuilder,private _flightService:FlightServiceService, private _dialog:MatDialog, private _bookingService:BookingService,private _router:Router) { }

  ngOnInit(): void {
    this.form = this._fb.group({
      formLocation: ['',[Validators.required]],
      toLocation: ['',[Validators.required]],
      fromDate: [new Date(''),[Validators.required]],
      toDate:[new Date(''),[Validators.required]]
    },{validators:dateValidator()})
  }


  flights:Flight[];

  searchFlight(){
    this.flights=[]
    const pipe = new DatePipe('en-US')
    // console.log(pipe.transform(this.form.value['fromDate'],'yyyy-MM-dd'))
    this._flightService.searchFlight(
      this.form.value['formLocation'],
      this.form.value['toLocation'],
      pipe.transform(this.form.value['fromDate'],'yyyy-MM-dd'),
      pipe.transform(this.form.value['toDate'],'yyyy-MM-dd')
    ).subscribe(
      data=>{
        this.flights = data;
        console.log(this.flights)
      },
      error => {
        console.log(error.error.error)
      }
    )
  }


  //table
  columns=[
    {
      columnDef:'flightId',
      header: 'Flight Id',
    },
    {
      columnDef : 'airlineName',
      header : 'Airline',
    },
    {
      columnDef : 'date',
      header : 'Date',
    },
    {
      columnDef : 'boardingTime',
      header : 'Boarding Time',
    },
    {
      columnDef : 'gateNo',
      header : 'Gate Number',
    },
    {
      columnDef : 'totalSeat',
      header : 'Available Seats',
    }
  ];



  //Flight Booking
  bookFlight(flight:Flight){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.data=flight;
    const dialog = this._dialog.open(BookflightComponent,dialogConfig)
    dialog.afterClosed().subscribe(
      data => {
        this._bookingService.reserve(data).subscribe(
          data => {
            Swal.fire({
              text:'Flight Reserved Successfully!!',
              icon:'success',
              timer:10000,
              showCancelButton:true,
              cancelButtonText:'Cancel'
            }).then(
              (result) => {
                this._router.navigate(['/dashboard'])
              }
            )
          }
        )
      }
    )
  }
}
