import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})
export class SemesterService {

  constructor(private http: HttpClient,
              private api: ApiService) { }

  getSemesters() {
    return this.http.get<Semester[]>(this.api.semesters());
  }

  createSemester() {
    return this.http.post(this.api.semesters(), {});
  }

  setCurrentSemester(semesterId: number) {
    return this.http.post(this.api.semesterCurrentState(semesterId), {});
  }

  getCurrentSemester() {
    return this.http.get<Semester>(this.api.currentSemester());
  }
}

export class Semester {
  id: number;
  year: number;
  period: number;
  current: boolean;
  name: string;
  fullName: string;
}
