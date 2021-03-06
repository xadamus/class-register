import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Subject} from './subjects.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private BASE_URL = 'http://localhost:8080/api';

  private AUTH_URL = `${this.BASE_URL}/public/auth/authenticate`;
  private USERS_URL = `${this.BASE_URL}/users`;

  private CURRENT_USER_URL = `${this.USERS_URL}/current`;
  private STUDENTS_URL = `${this.BASE_URL}/students`;

  private TEACHERS_URL = `${this.BASE_URL}/teachers`;

  private SEMESTERS_URL = `${this.BASE_URL}/semesters`;

  private SUBJECTS_URL = `${this.BASE_URL}/subjects`;

  private PROFILES_URL = `${this.BASE_URL}/profiles`;

  private ALL_SUBJECTS_URL = `${this.BASE_URL}/subjects/all`;

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

  studentMarks(studentId: number) {
    return this.student(studentId) + '/marks';
  }

  teachers(): string {
    return this.TEACHERS_URL;
  }

  teacher(teacherId): string {
    return this.TEACHERS_URL + '/' + teacherId;
  }

  semesters(): string {
    return this.SEMESTERS_URL;
  }

  semesterCurrentState(semesterId: number): string {
    return this.SEMESTERS_URL + '/' + semesterId +  '/current';
  }

  currentSemester(): string {
    return this.SEMESTERS_URL + '/current';
  }

  subjects() {
    return this.SUBJECTS_URL;
  }

  subject(subjectId: number) {
    return this.SUBJECTS_URL + '/' + subjectId;
  }

  profiles() {
    return this.PROFILES_URL;
  }

  profile(profileId: number) {
    return this.PROFILES_URL + '/' + profileId;
  }

  profileAllocations(profileId: number) {
    return this.PROFILES_URL + '/' + profileId + '/allocations';
  }

  teacherProfileAllocations(teacherId: number, semesterId: number) {
    return this.teacher(teacherId) + '/allocations?semesterId=' + semesterId;
  }

  profileAllocation(profileId: number, allocationId: number) {
    return this.profileAllocations(profileId) + '/' + allocationId;
  }

  profileMemberships(profileId: number) {
    return this.PROFILES_URL + '/' + profileId + '/memberships';
  }

  profileMembershipsBySubject(profileId: number, subjectId: number) {
    return this.PROFILES_URL + '/' + profileId + '/memberships?subjectId=' + subjectId;
  }

  profileMembership(profileId: number, membershipId: number) {
    return this.profileMemberships(profileId) + '/' + membershipId;
  }

  getAllSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.ALL_SUBJECTS_URL);
  }

  marks(membershipId: number, subjectId: number) {
    return this.profileMembership(0, membershipId) + '/marks?subjectId=' + subjectId;
  }

  mark(membershipId: number, subjectId: number, markId: number) {
    return this.profileMembership(0, membershipId) + '/marks/' + markId + '?subjectId=' + subjectId;
  }
}

export class ApiResponseDto {
  success: boolean;
  message: string;
}
