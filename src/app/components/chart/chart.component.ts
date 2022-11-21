import { Component, Input, OnInit } from '@angular/core';
import { BookingService } from 'src/app/services/booking.service';
import { Chart } from 'src/app/util-classes/chartdata';
import { User } from 'src/app/util-classes/user';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  constructor(private _booking:BookingService) { }
  @Input() user:User;

  ngOnInit(): void {
   //this.loadChartData();
  }

  chart_data:Chart[]=[
    {
      name:"JANUARY",
      value:"0"
    },
    {
      name:"FEBRUARY",
      value:"10"
    },
    {
      name:"MARCH",
      value:"10"
    },
    {
      name:"APRIL",
      value:"10"
    },
    {
      name:"MAY",
      value:'11'
    },
    {
      name:"JUNE",
      value:"1"
    },
    {
      name:"JULY",
      value:"0"
    },
    {
      name:"AUGUST",
      value:"1"
    },
    {
      name:"SEPTEMBER",
      value:"1"
    },
    {
      name:"OCTOBER",
      value:"2"
    },
    {
      name:"NOVEMBER",
      value:"1"
    },
    {
      name:"DECEMBER",
      value:"0"
    }
  ];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Month';
  showYAxisLabel = true;
  yAxisLabel = 'Bookings';

  /*
  loadChartData():Chart[]{
    // left f
    console.clear();
    //this.chart_data=[]
    this._booking.getChart(this.user.email).subscribe(
      data => {
        data.forEach(chart_item=>{
          this.chart_data.forEach(entry=>{
            if(entry.name===chart_item.name)
              entry.value=chart_item.value;
          })
          //console.log(chart_item)
        })
      }
    )
    console.log(this.chart_data)
    //window.location.reload();
    return this.chart_data;
  }
*/
}
