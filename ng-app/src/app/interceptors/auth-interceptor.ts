import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthService} from '../services/auth.service';
import {Observable, of} from 'rxjs';
import {Router} from '@angular/router';
import {catchError, retry} from 'rxjs/operators';

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
      return next.handle(authReq).pipe(
        retry(1),
        catchError(this.handleAuthError)
      );
    } else {
      return next.handle(req).pipe(
        retry(1),
        catchError(this.handleAuthError)
      );
    }
  }

  private handleAuthError(err: HttpErrorResponse): Observable<any> {
    if (err.status === 401 || err.status === 403) {
      this.router.navigate(['/login']);
      return of(err.message);
    }
    return Observable.throw(err);
  }
}
