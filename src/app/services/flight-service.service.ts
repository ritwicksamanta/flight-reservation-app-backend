import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Flight } from '../util-classes/Flight';
import { FlightAddRequest } from '../util-classes/flightreq';

@Injectable({
  providedIn: 'root'
})
export class FlightServiceService {

  constructor(private _http:HttpClient) { }

  private  APP_URL = 'http://127.0.0.1:8002/api/flight';

  public searchFlight(fromLocation:string,toLocation:string,fromDate:string,toDate:string):Observable<Flight[]>{
    const queryParams = {fromLocation,toLocation,fromDate,toDate}
    return this._http.get<Flight[]>(`${this.APP_URL}/search`,{params:queryParams});
  }

  public addFlight(flightDetails:FlightAddRequest):Observable<any>{
    return this._http.post(`${this.APP_URL}/add`,flightDetails);
  }
}
