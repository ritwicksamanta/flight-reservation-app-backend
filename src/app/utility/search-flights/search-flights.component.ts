import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { SearchRequest } from 'src/app/util-classes/search-request';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-search-flights',
  templateUrl: './search-flights.component.html',
  styleUrls: ['./search-flights.component.css'],
  providers:[
  ]
})
export class SearchFlightsComponent implements OnInit {

  searchFlights:FormGroup;
  constructor(private _dialogRef:MatDialogRef<SearchFlightsComponent>,private _fb:FormBuilder) { }

  ngOnInit(): void {
    this.searchFlights = this._fb.group({
      from:['',[Validators.required]],
      to:['',[Validators.required]],
      date:[new Date(),[Validators.required]]
    })
  }

  /*searchFlights:FormGroup = new FormGroup(
    {
      from:new FormControl('',Validators.required),
      to:new FormControl('',Validators.required),
      date:new FormControl('',Validators.required)
    }
  )*/
  _minDate:Date=new Date();

  search(value:SearchRequest){
    value.date = new DatePipe('en-US').transform(value.date,'dd/MM/yyyy');
    this._dialogRef.close(value);
  }
  log(str:string){
    console.log('closed');
   this._dialogRef.close();
  }
}
