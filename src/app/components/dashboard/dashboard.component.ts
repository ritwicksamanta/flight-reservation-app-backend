import { JsonPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BookingService } from 'src/app/services/booking.service';
import { FlightServiceService } from 'src/app/services/flight-service.service';
import { LoginService } from 'src/app/services/login.service';
import { Customer } from 'src/app/util-classes/customer';
import { User } from 'src/app/util-classes/user';
import Swal from 'sweetalert2';
import { AddFlightComponent } from '../add-flight/add-flight.component';
import { ProfileComponent } from '../profile/profile.component';
import { ViewBookingsComponent } from '../view-bookings/view-bookings.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  user:User;
  constructor(public _router:Router,private _login:LoginService, private readonly dialog:MatDialog, private _flight:FlightServiceService,private _booking:BookingService) { }

  user_name:string;
  ngOnInit(): void {
    this._login.getUser().subscribe(
      data => {
        console.log(data)
        this.user = data as User;
        this.user_name = `${this.user.firstName} ${this.user.lastName}`
      }
    )
    //this.setChartConfig();
  }

  // for charts only
  /**
   * chart start
   */

  /**
   * Recent bookings table
   */




  //displayedColumns = this.columns.map(column => column.columnDef);

  openProfileView():void{
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.data=this.user;
    dialogConfig.width='500px';

    const dialog = this.dialog.open(ProfileComponent,dialogConfig);
  }

  addFlightDialog():void{
    console.clear();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.width='800px'
    const dialog = this.dialog.open(AddFlightComponent,dialogConfig);

    dialog.afterClosed().subscribe(
      data=>{
        console.log('dashboard',data);
        this._flight.addFlight(data).subscribe(
          data => Swal.fire(
            {
              title: 'Added Successfully',
              text: data.message,
              icon:'success',
              timer:3000,
              showCloseButton:true
            }
          )
        )
      }
    )
  }

  viewAllBookings(){
    console.clear();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.closeOnNavigation=true;
    dialogConfig.data=this.user.email;
    const dialog=this.dialog.open(ViewBookingsComponent,dialogConfig);
  }
}
