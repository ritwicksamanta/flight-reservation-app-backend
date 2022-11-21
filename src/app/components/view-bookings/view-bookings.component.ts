import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import { Booking } from 'src/app/util-classes/Booking';
import { BookingService } from 'src/app/services/booking.service';
import { CancelBookingComponent } from '../cancel-booking/cancel-booking.component';

@Component({
  selector: 'app-view-bookings',
  templateUrl: './view-bookings.component.html',
  styleUrls: ['./view-bookings.component.css']
})
export class ViewBookingsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) readonly data:string,private _booking:BookingService,private readonly _dialog:MatDialog,private dialogRef:MatDialogRef<ViewBookingsComponent>) { }
  bookings:Booking[];
  ngOnInit(): void {
    this.bookings=[]
    this._booking.getBookingForCustomer(this.data).subscribe(
      bookings => {
        bookings.forEach(element => {
          //console.log(element)
          const booking:Booking =  new Booking()
          booking.bookingId = element.booking.bookingId;
          booking.bookingDate = element.booking.bookingDate;
          booking.fromLocation = element.flightDetails.fromLocation;
          booking.toLocation = element.flightDetails.toLocation;
          booking.boardingTime = element.flightDetails.boardingTime;
          booking.date = element.flightDetails.date;
          booking.gateNo = element.flightDetails.gateNo;
          booking.seatNo=element.booking.seatNo;
          console.log(booking)
          this.bookings.push(booking)
        }
    )
    })
  }


  //cancel
  doCancel(booking:Booking){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.data=booking;
    dialogConfig.width='600px'
    const dialog = this._dialog.open(CancelBookingComponent,dialogConfig);
    dialog.afterClosed().subscribe(data=>this.dialogRef.close())
  }
}
