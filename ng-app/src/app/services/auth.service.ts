import {EventEmitter, Injectable, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Teacher} from './teachers.service';
import {Student} from './students.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  @Output() change: EventEmitter<any> = new EventEmitter();

  private TOKEN_SESSION_KEY = 'access_token';
  private TOKEN_TYPE_SESSION_KEY = 'token_type';
  private USER_SESSION_KEY = 'user_data';

  constructor(private http: HttpClient,
              private api: ApiService) { }

  public login(username: string, password: string) {
    const tokenObservable = this.http.post<Token>(this.api.authenticate(), {username, password});
    const newObservable = new Observable((observer) => {
      tokenObservable.subscribe(response => {
        localStorage.setItem(this.TOKEN_SESSION_KEY, response.accessToken);
        localStorage.setItem(this.TOKEN_TYPE_SESSION_KEY, response.tokenType);
        this.retrieveCurrentUser(observer);
      }, error => {
        observer.error();
      });
    });
    return newObservable;
  }

  public getCurrentUser(): User {
    return JSON.parse(localStorage.getItem(this.USER_SESSION_KEY));
  }

  public getToken() {
    return localStorage.getItem(this.TOKEN_SESSION_KEY);
  }

  public getTokenType() {
    return localStorage.getItem(this.TOKEN_TYPE_SESSION_KEY);
  }

  public logout() {
    localStorage.removeItem(this.TOKEN_SESSION_KEY);
    localStorage.removeItem(this.TOKEN_TYPE_SESSION_KEY);
    localStorage.removeItem(this.USER_SESSION_KEY);
    this.change.emit();
  }

  public authenticated() {
    return localStorage.getItem(this.TOKEN_SESSION_KEY) != null;
  }

  public hasRole(role: string): boolean {
    const currentUser = this.getCurrentUser();
    return this.authenticated() && currentUser != null && currentUser.roles.indexOf(role) > -1;
  }

  private retrieveCurrentUser(observer) {
    this.http.get<User>(this.api.currentUser()).subscribe(response => {
      localStorage.setItem(this.USER_SESSION_KEY, JSON.stringify(response));
      this.change.emit();
      observer.next();
    }, error => {
      localStorage.removeItem(this.USER_SESSION_KEY);
      this.change.emit();
      observer.error();
    });
  }
}

export class User {
  id: number;
  username: string;
  password: string;
  email: string;
  roles: string[] = [];
  teacher: Teacher;
  student: Student;
}

export class Token {
  accessToken: string;
  tokenType: string;
}
