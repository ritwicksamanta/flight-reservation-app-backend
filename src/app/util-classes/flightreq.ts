/**
 * "airlineName":"Spice Jet",
    "toLocation":"Kolkata",
    "fromLocation":"Mumbai",
    "boardingTime": "08:30",
    "date":"2022-10-09",
    "totalSeat":"100"
 */

export class FlightAddRequest{
  airlinrName!:string;
  fromLocation!:string;
  toLocation!:string;
  boardingTime!:string;
  date!:string;
  totalSeat!:number;
  gateNo!:number;
}
