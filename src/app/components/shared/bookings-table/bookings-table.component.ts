import { Component, OnInit, ViewChild, AfterViewInit, Input } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { BookingService } from 'src/app/services/booking.service';
import { Booking } from 'src/app/util-classes/Booking';

@Component({
  selector: 'app-bookings-table',
  templateUrl: './bookings-table.component.html',
  styleUrls: ['./bookings-table.component.css']
})



export class BookingsTableComponent implements OnInit,AfterViewInit {

  @ViewChild(MatSort) sort: MatSort;
  dataSource:MatTableDataSource<Booking> = new MatTableDataSource<Booking>([]);


  bookings:Booking[]=[
    // {
    //   bookingId:'B123',
    //   fromLocation:'Kolkata',
    //   toLocation:'Delhi',
    //   date:'2022/3/11'
    // },
    // {
    //   bookingId:'B122',
    //   fromLocation:'Kolkata',
    //   toLocation:'Delhi',
    //   date:'2022/3/11'
    // }
  ]
  @Input() email:string;
  constructor(private _service:BookingService) { }
  ngAfterViewInit(): void {

    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.bookings=[]
    this._service.getBookingForCustomer(this.email).subscribe(
      data=>{
        data.forEach(element => {
          const booking = new Booking();
          booking.bookingId=element.booking.bookingId;
          booking.fromLocation=element.flightDetails.fromLocation;
          booking.toLocation = element.flightDetails.toLocation;
          booking.date = element.flightDetails.date;
          booking.bookingDate = element.booking.bookingDate;
          this.bookings.push(booking);
        });
      }
    )
    this._loadDataSource();
  }

  columns=[
    {
      columnDef:'bookingId',
      header : 'Booking Id',
      cell : (element:Booking) => `${element.bookingId}`
    },
    {
      columnDef: 'fromLocation',
      header: 'Form',
      cell : (element:Booking) => `${element.fromLocation}`
    },
    {
      columnDef: 'toLocaion',
      header: 'to',
      cell : (element:Booking) => `${element.toLocation}`
    },
    {
      columnDef: 'date',
      header: 'Date Of Journey',
      cell : (element:Booking) => `${element.date}`
    },
    {
      columnDef: 'bookingDate',
      header: 'Date Of Reservation',
      cell : (element:Booking) => `${element.bookingDate}`
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef);

  private _loadDataSource(){
    this.dataSource = new MatTableDataSource(this.bookings);
  }



  sort_data($event){

  }

}
