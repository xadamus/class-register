import {Injectable} from '@angular/core';
import {ApiResponseDto, ApiService} from './api.service';
import {HttpClient} from '@angular/common/http';
import {Semester} from './semester.service';
import {Subject} from './subjects.service';
import {Teacher} from './teachers.service';
import {Student} from './students.service';

@Injectable({
  providedIn: 'root'
})
export class ProfilesService {

  constructor(private api: ApiService,
              private http: HttpClient) {
  }

  getProfiles() {
    return this.http.get<Profile[]>(this.api.profiles());
  }

  saveProfile(profile: Profile) {
    if (profile.id != null) {
      return this.http.put<ApiResponseDto>(this.api.profile(profile.id), profile);
    } else {
      return this.http.post<ApiResponseDto>(this.api.profiles(), profile);
    }
  }

  deleteProfile(profile: Profile) {
    return this.http.delete<ApiResponseDto>(this.api.profile(profile.id));
  }

  getProfileAllocations(profile: Profile) {
    return this.http.get<Allocation[]>(this.api.profileAllocations(profile.id));
  }

  deleteProfileAllocation(allocation: Allocation) {
    return this.http.delete<ApiResponseDto>(this.api.profileAllocation(allocation.profile.id, allocation.id));
  }

  createProfileAllocation(allocation: AllocationCreationDto) {
    return this.http.post<ApiResponseDto>(this.api.profileAllocations(allocation.profileId), allocation);
  }

  getProfileMemberships(profile: Profile) {
    return this.http.get<Membership[]>(this.api.profileMemberships(profile.id));
  }

  deleteProfileMembership(membership: Membership) {
    return this.http.delete<ApiResponseDto>(this.api.profileMembership(membership.profile.id, membership.id));
  }

  createProfileMembership(membership: MembershipCreationDto) {
    return this.http.post<ApiResponseDto>(this.api.profileMemberships(membership.profileId), membership);
  }

  getTeacherProfileAllocations(teacher: Teacher, semester: Semester) {
    return this.http.get<Allocation[]>(this.api.teacherProfileAllocations(teacher.id, semester.id));
  }
}

export class Profile {
  id: number;
  name: string;
  level: number;
}

export class Allocation {
  id: number;
  profile: Profile;
  semester: Semester;
  subject: Subject;
  teacher: Teacher;
}

export class AllocationCreationDto {
  semesterId: number;
  teacherId: number;
  profileId: number;
  subjectId: number;
}

export class Membership {
  id: number;
  semester: Semester;
  student: Student;
  profile: Profile;
}

export class MembershipCreationDto {
  semesterId: number;
  studentId: number;
  profileId: number;
}
