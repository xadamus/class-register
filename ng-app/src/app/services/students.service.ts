import {Injectable} from '@angular/core';
import {ApiResponseDto, ApiService} from './api.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StudentsService {
  constructor(private api: ApiService,
              private http: HttpClient) { }

  getStudents() {
    return this.http.get<Student[]>(this.api.students());
  }

  save(studentData) {
    const studentCreationDto = new StudentCreationDto();
    studentCreationDto.firstName = studentData.firstName;
    studentCreationDto.lastName = studentData.lastName;
    studentCreationDto.userId = studentData.userId;
    if (studentData.id != null) {
      return this.http.put<ApiResponseDto>(this.api.student(studentData.id), studentCreationDto);
    } else {
      return this.http.post<ApiResponseDto>(this.api.students(), studentCreationDto);
    }
  }

  delete(studentData) {
    return this.http.delete<ApiResponseDto>(this.api.student(studentData.id));
  }
}

export class Student {
  id: number;
  firstName: string;
  lastName: string;
  fullName: string;
  username: string;
  userId: number;
}

export class StudentCreationDto {
  firstName: string;
  lastName: string;
  userId: number;
}
