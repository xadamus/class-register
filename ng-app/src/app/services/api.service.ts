import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Subject} from '../pages/marks/model/subject';
import {ContactViewModel} from '../pages/contact/contact.component';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private BASE_URL = 'http://localhost:8080/api';

  private USERS_URL = `${this.BASE_URL}/users`;
  private CURRENT_USER_URL = `${this.USERS_URL}/current`;

  private STUDENTS_URL = `${this.BASE_URL}/students`;

  private AUTH_URL = `${this.BASE_URL}/public/auth/authenticate`;

  private ALL_SUBJECTS_URL = `${this.BASE_URL}/subjects/all`;
  private CONTACT_URL = `${this.BASE_URL}/contact`;

  constructor(private http: HttpClient) {
  }

  authenticate(): string {
    return this.AUTH_URL;
  }

  currentUser(): string {
    return this.CURRENT_USER_URL;
  }

  users(): string {
    return this.USERS_URL;
  }

  freeUsers(): string {
    return this.USERS_URL + '/free';
  }

  user(userId): string {
    return this.USERS_URL + '/' + userId;
  }

  students(): string {
    return this.STUDENTS_URL;
  }

  student(studentId): string {
    return this.STUDENTS_URL + '/' + studentId;
  }

  getAllSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.ALL_SUBJECTS_URL);
  }

  sendMessage(message: ContactViewModel): Observable<any> {
    return this.http.post(this.CONTACT_URL, message);
  }
}

export class ApiResponseDto {
  success: boolean;
  message: string;
}
