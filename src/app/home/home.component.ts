import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  home_bg='/assets/images/bg_home.png';

  constructor() { }

  ngOnInit(): void {
  }

}
