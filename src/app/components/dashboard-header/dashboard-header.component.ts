import { Component, Input, OnChanges, OnInit, Output, SimpleChanges, EventEmitter } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { Customer } from 'src/app/util-classes/customer';

@Component({
  selector: 'app-dashboard-header',
  templateUrl: './dashboard-header.component.html',
  styleUrls: ['./dashboard-header.component.css']
})
export class DashboardHeaderComponent implements OnInit,OnChanges {

  //@Input() expandedDrawer:boolean;
  // @Output() expanded = new EventEmitter<{expand:boolean}>();

  @Input() expanded:boolean;
  //@Input() currentUser:Customer;
  @Input() user_name:string;
  constructor(private _router:Router) {

  }

  ngOnInit(): void {

  }
  _isExpanded:boolean = false;
  ngOnChanges(changes: SimpleChanges): void {
      //console.log(changes);
      // if(changes['expanded']!=undefined)
      //   this._isExpanded = true;
      // else this._isExpanded=false;
      this._isExpanded = (changes['expanded'].currentValue)? changes['expanded'].currentValue : false;
      //console.log(this._isExpanded);

  }

  logout():void{
    sessionStorage.clear();
    this._router.navigate(['/'])
  }

}
