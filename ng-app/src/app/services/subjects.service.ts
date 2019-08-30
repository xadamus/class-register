import {Injectable} from '@angular/core';
import {ApiResponseDto, ApiService} from './api.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SubjectsService {

  constructor(private http: HttpClient,
              private api: ApiService) { }

  getSubjects() {
    return this.http.get<Subject[]>(this.api.subjects());
  }

  save(subject) {
    if (subject.id != null) {
      return this.http.put<ApiResponseDto>(this.api.subject(subject.id), subject);
    } else {
      return this.http.post<ApiResponseDto>(this.api.subjects(), subject);
    }
  }

  delete(subject) {
    return this.http.delete<ApiResponseDto>(this.api.subject(subject.id));
  }

}

export class Subject {
  id: number;
  name: string;
}
