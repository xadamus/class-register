import {Injectable} from '@angular/core';
import {ApiResponseDto, ApiService} from './api.service';
import {User} from './auth.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  roles = ['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_PARENT', 'ROLE_STUDENT'];

  constructor(private api: ApiService,
              private http: HttpClient) { }

  getUsers() {
    return this.http.get<User[]>(this.api.users());
  }

  getFreeUsers() {
    return this.http.get<User[]>(this.api.freeUsers());
  }

  save(userData) {
    const userCreationDto = new UserCreationDto();
    userCreationDto.username = userData.username;
    userCreationDto.password = userData.password;
    userCreationDto.email = userData.email;
    userCreationDto.roles = userData.roles;
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
  password: string;
  email: string;
  roles: string[];
}
