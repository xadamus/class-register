import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthService} from '../shared/auth.service';
import {Observable, of} from 'rxjs';
import {Router} from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private auth: AuthService,
              private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const authToken = this.auth.getToken();
    const authTokenType = this.auth.getTokenType();
    if (authToken && authTokenType) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', authTokenType + ' ' + authToken)
      });
      const observable = next.handle(authReq);
      observable.subscribe(null, error => this.handleAuthError(error))
      return observable;
    } else {
      const observable = next.handle(req);
      observable.subscribe(null, error => this.handleAuthError(error))
      return observable;
    }
  }

  private handleAuthError(err: HttpErrorResponse): Observable<any> {
    if (err.status === 401 || err.status === 403) {
      this.router.navigateByUrl(`/login`);
      return of(err.message);
    }
    return Observable.throw(err);
  }
}
