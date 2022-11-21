import { LoginRequest } from './../util-classes/login-request';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable,map } from 'rxjs';
import { Customer } from '../util-classes/customer';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _http:HttpClient) { }

  private app_url = 'http://127.0.0.1:8001/api'

  login(request:LoginRequest):Observable<any>{
    return this._http.post(`${this.app_url}/login`,request);
  }

  register(request:Customer):Observable<any>{
    return this._http.post(`${this.app_url}/register`,request);
  }
  getUser():Observable<any>{
    //let user:Customer;
    return this._http.get(`${this.app_url}/get/user`)
  }

  isLoggedIn():boolean{
    console.log(sessionStorage.getItem('token')!==null)
    return sessionStorage.getItem('token')!==null;
  }
}
