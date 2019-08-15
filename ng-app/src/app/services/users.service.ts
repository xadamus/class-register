import {Injectable} from '@angular/core';
import {ApiResponseDto, ApiService} from './api.service';
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

  save(userData) {
    const userCreationDto = new UserCreationDto();
    userCreationDto.username = userData.username;
    userCreationDto.email = userData.email;
    if (userData.id != null) {
      return this.http.put<ApiResponseDto>(this.api.user(userData.id), userCreationDto);
    } else {
      return this.http.post<ApiResponseDto>(this.api.users(), userCreationDto);
    }
  }

  delete(userData) {
    return this.http.delete<ApiResponseDto>(this.api.user(userData.id));
  }
}

export class UserCreationDto {
  username: string;
  password = 'testing';
  email: string;
}
