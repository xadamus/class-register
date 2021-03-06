import {Injectable} from '@angular/core';
import {ApiResponseDto, ApiService} from './api.service';
import {HttpClient} from '@angular/common/http';
import {Allocation} from './profiles.service';

@Injectable({
  providedIn: 'root'
})
export class TeachersService {
  private currentAllocation: Allocation;

  constructor(private api: ApiService,
              private http: HttpClient) { }

  getTeachers() {
    return this.http.get<Teacher[]>(this.api.teachers());
  }

  save(teacherData) {
    const teacherCreationDto = new TeacherCreationDto();
    teacherCreationDto.firstName = teacherData.firstName;
    teacherCreationDto.lastName = teacherData.lastName;
    teacherCreationDto.userId = teacherData.userId;
    if (teacherData.id != null) {
      return this.http.put<ApiResponseDto>(this.api.teacher(teacherData.id), teacherCreationDto);
    } else {
      return this.http.post<ApiResponseDto>(this.api.teachers(), teacherCreationDto);
    }
  }

  delete(teacherData) {
    return this.http.delete<ApiResponseDto>(this.api.teacher(teacherData.id));
  }


  getCurrentAllocation(): Allocation {
    return this.currentAllocation;
  }

  setCurrentAllocation(value: Allocation) {
    this.currentAllocation = value;
  }
}

export class Teacher {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  userId: number;
}

export class TeacherCreationDto {
  firstName: string;
  lastName: string;
  userId: number;
}
