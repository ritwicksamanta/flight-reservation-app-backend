import { Component, OnInit } from '@angular/core';
import {BreakpointObserver,Breakpoints} from '@angular/cdk/layout'

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  cols!:number;
  gridByBreakPoints = {
    xl:2,
    lg:2,
    md:2,
    sm:1,
    xs:1
  }

  constructor(private breakPointObserver: BreakpointObserver) {
    this.breakPointObserver.observe([
      Breakpoints.XSmall,
      Breakpoints.Small,
      Breakpoints.Medium,
      Breakpoints.Large,
      Breakpoints.XLarge
    ]).subscribe(
      result => {
        if(result.matches){
          if(result.breakpoints[Breakpoints.XSmall])
            this.cols = this.gridByBreakPoints.xs;
          if(result.breakpoints[Breakpoints.Small])
            this.cols = this.gridByBreakPoints.sm;
          if(result.breakpoints[Breakpoints.Medium])
            this.cols = this.gridByBreakPoints.md;
          if(result.breakpoints[Breakpoints.Large])
            this.cols = this.gridByBreakPoints.lg;
          if(result.breakpoints[Breakpoints.XLarge])
            this.cols = this.gridByBreakPoints.xl;
        }
      }
    )
   }

  ngOnInit(): void {
  }
   image_uri = '../../assets/images/error.png';
  error_img = 'assets/images/error-bg.jpg';

  onEvent(event:Event){
    event.target
  }

}
