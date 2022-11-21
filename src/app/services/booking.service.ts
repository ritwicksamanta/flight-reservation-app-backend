import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Booking } from '../util-classes/Booking';
import { Chart } from '../util-classes/chartdata';
import { FlightBookRequest } from '../util-classes/FlightBookreq';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private _http:HttpClient) { }
  private APP_URL = 'http://127.0.0.1:8009/api/reservations'

  reserve(data:FlightBookRequest):Observable<any>{
    return this._http.post(`${this.APP_URL}/reserve`,data);
  }
  getBookingForCustomer(email:string):Observable<any>{
    return this._http.get<any[]>(`${this.APP_URL}/get/customer/all`,{
      params:{id:email}
    })
  }
  cancelBooking(id:string):Observable<any>{
    return this._http.delete(`${this.APP_URL}/cancel/booking`,{
      params:{id}
    })
  }

  //GET CHART DATA
  getChart(id:string):Observable<Chart[]>{
    return this._http.get<Chart[]>(`${this.APP_URL}/get/stats`,{params:{id}})
  }
}
