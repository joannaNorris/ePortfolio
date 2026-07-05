import { Injectable,Provider } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Authentication } from '../services/authentication';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authentication: Authentication) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      var isAuthAPI: boolean;
      //console.log('Interceptor::URL' + req.url);
    if (req.url.startsWith('login') ||
        req.url.startsWith('register')) {
      isAuthAPI = true;
    }
    else {
      isAuthAPI = false;
    }

    if (this.authentication.isLoggedIn() && !isAuthAPI) {
      let token = this.authentication.getToken();
      //console.log(token);
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(authReq);
    }
    return next.handle(req);
  }
}

export const authInterceptorProvider: Provider = {
  provide: HTTP_INTERCEPTORS,
  useClass: JwtInterceptor,
  multi: true
};

