import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { DashboardComponent } from './../../components/dashboard/dashboard.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from 'src/app/login/login.component';
import { HeaderComponent } from 'src/app/header/header.component';
import { MatButtonModule } from '@angular/material/button';
import { SearchComponent } from 'src/app/components/search/search.component';
import { WrapperComponent } from 'src/app/components/wrapper/wrapper.component';
import { BookingsTableComponent } from 'src/app/components/shared/bookings-table/bookings-table.component';


const routes:Routes = [
  {
    path:'',
    component:WrapperComponent,
    children:[
      {
        path:'',
        component:DashboardComponent,
      },
      {
        path:'search',
        component:SearchComponent,

      }
    ]
  }
];


@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    NgxMaterialTimepickerModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class DashboardModule { }
