import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog'
import { Router } from '@angular/router';
import { SearchFlightsComponent } from '../utility/search-flights/search-flights.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  logo = '/assets/images/logo.png'
  navbarCollapsed = true;

  constructor(private _dialog : MatDialog, private _route:Router) { }

  ngOnInit(): void {
    console.log(this._route.url)
  }

  toggleNavbarCollapse(){
    this.navbarCollapsed = !this.navbarCollapsed;
    console.log(this.navbarCollapsed)
  }
  isHomePage(){
    return this._route.url==='/home';
  }

  isLoginPage(){
    return this._route.url === '/login';
  }

  isRegisterPage = ()=>this._route.url === '/register';
  searchFlights(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.autoFocus=true;

    const dialog = this._dialog.open(SearchFlightsComponent,dialogConfig);

    dialog.afterClosed().subscribe(
      data => {
        if(data)
          console.log(data);
      }
    )
  }
}
