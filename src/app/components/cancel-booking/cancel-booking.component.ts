import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
 import { Booking } from 'src/app/util-classes/Booking';
import { BookingService } from 'src/app/services/booking.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cancel-booking',
  templateUrl: './cancel-booking.component.html',
  styleUrls: ['./cancel-booking.component.css']
})
export class CancelBookingComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) readonly data:Booking,private _service:BookingService,private dialogRef:MatDialogRef<CancelBookingComponent>) { }

  ngOnInit(): void {
  }
  cancel(){
    this._service.cancelBooking(this.data.bookingId).subscribe(
      data=>{
        Swal.fire({
          text:'Reservation Deleted',
          icon:'info',
          color:'#ff0000',
          showCancelButton:true,
          cancelButtonText:'Cancel'
        }).then(result=>this.dialogRef.close());
      })

    }

}
