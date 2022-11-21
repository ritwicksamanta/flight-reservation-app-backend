import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { Customer } from 'src/app/util-classes/customer';

@Component({
  selector: 'app-wrapper',
  templateUrl: './wrapper.component.html',
  styleUrls: ['./wrapper.component.css']
})
export class WrapperComponent implements OnInit {

  constructor(private _login:LoginService) { }

  currentUser:Customer;
  user_name:string;
  ngOnInit(): void {
    this._login.getUser().subscribe(
      data => {
        this.currentUser=data;
        this.user_name = `${this.currentUser.firstName} ${this.currentUser.lastName}`
      }
    )
  }
  expandedDrawer:boolean;
  onExpanded(event){
    //console.log(event.expanded)
    this.expandedDrawer = event.expanded;
  }
}
