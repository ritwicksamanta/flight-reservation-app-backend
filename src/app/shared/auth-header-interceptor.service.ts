import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class AuthHeaderInterceptorService implements HttpInterceptor {

  constructor() { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //const API_KEY='12345'

    if(!(req.url.includes('login') || req.url.includes('register'))  &&  sessionStorage.getItem('token')!=null){
      const Authorization = sessionStorage.getItem('token')
      return next.handle(req.clone({setHeaders:{Authorization}}))
    }

    return next.handle(req);
  }
}
