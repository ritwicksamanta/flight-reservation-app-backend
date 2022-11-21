import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from 'src/app/util-classes/customer';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  //@Input() expand:boolean;// hold value passed from parent
  isExpanded:boolean=false;
  @Output() expand = new EventEmitter<{expanded:boolean}>();
  constructor(public _router:Router) { }

  // ngOnChanges(changes: SimpleChanges): void {
  //   console.log(changes['expand'].currentValue);
  //   this.isExpanded = changes['expand'].currentValue?
  //   changes['expand'].currentValue:false; //it will check for changes
  // }

  ngOnInit(): void {
  }



}
