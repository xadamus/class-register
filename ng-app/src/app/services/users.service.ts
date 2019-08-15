import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {User} from './auth.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private api: ApiService,
              private http: HttpClient) { }

  getUsers() {
    return this.http.get<User[]>(this.api.users());
  }
}
