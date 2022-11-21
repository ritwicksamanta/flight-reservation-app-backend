import { MatNativeDateModule } from '@angular/material/core';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent} from './home/home.component'
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import { HeaderComponent } from './header/header.component';
import { SearchFlightsComponent } from './utility/search-flights/search-flights.component';
import { MatDialogModule } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker'
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { LoginComponent } from './login/login.component';
import { AutofocusDirective } from './shared/autofocus.directive'
import { MatFormFieldControl, MatFormFieldModule } from '@angular/material/form-field';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DashboardModule } from './modules/dashboard/dashboard.module';
import { DashboardHeaderComponent } from './components/dashboard-header/dashboard-header.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import { MatDividerModule } from '@angular/material/divider';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { SearchComponent } from './components/search/search.component';
import { WrapperComponent } from './components/wrapper/wrapper.component';
import {MatMenuModule} from '@angular/material/menu';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { MatTableModule } from '@angular/material/table';
import { BookingsTableComponent } from './components/shared/bookings-table/bookings-table.component';
import { MatSortModule } from '@angular/material/sort';
import { AuthHeaderInterceptorService } from './shared/auth-header-interceptor.service';
import { ProfileComponent } from './components/profile/profile.component';
import { AddFlightComponent } from './components/add-flight/add-flight.component';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { NgxMatTimepickerModule } from 'ngx-mat-timepicker';
import { BookflightComponent } from './components/bookflight/bookflight.component';
import { MatSelectModule } from '@angular/material/select';
import { ViewBookingsComponent } from './components/view-bookings/view-bookings.component';
import { CancelBookingComponent } from './components/cancel-booking/cancel-booking.component';
import { ChartComponent } from './components/chart/chart.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PageNotFoundComponent,
    HeaderComponent,
    SearchFlightsComponent,
    LoginComponent,
    AutofocusDirective,
    RegisterComponent,
    DashboardComponent,
    DashboardHeaderComponent,
    SidenavComponent,
    SearchComponent,
    WrapperComponent,
    BookingsTableComponent,
    ProfileComponent,
    AddFlightComponent,
    BookflightComponent,
    ViewBookingsComponent,
    CancelBookingComponent,
    ChartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatGridListModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    FormsModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    HttpClientModule,
    FlexLayoutModule,
    MatCheckboxModule,
    DashboardModule,
    MatSidenavModule,
    MatIconModule,
    MatDividerModule,
    MatToolbarModule,
    MatListModule,
    MatMenuModule,
    MatGridListModule,
    NgxChartsModule,
    MatTableModule,
    MatSortModule,
    NgxMatTimepickerModule,
    MatSelectModule
  ],
  providers: [
    {provide:HTTP_INTERCEPTORS, useClass: AuthHeaderInterceptorService, multi:true}
  ],
  bootstrap: [AppComponent],
  exports: []
})
export class AppModule {

 }
