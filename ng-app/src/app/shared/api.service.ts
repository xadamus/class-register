import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Subject} from '../marks/model/subject';
import {ContactViewModel} from '../contact/contact.component';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private BASE_URL = 'http://localhost:8080/api';
  private ALL_SUBJECTS_URL = `${this.BASE_URL}/subjects/all`;
  private CONTACT_URL = `${this.BASE_URL}/contact`;

  constructor(private http: HttpClient) {
  }

  getAllSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.ALL_SUBJECTS_URL);
  }

  sendMessage(message: ContactViewModel): Observable<any> {
    return this.http.post(this.CONTACT_URL, message);
  }
}
