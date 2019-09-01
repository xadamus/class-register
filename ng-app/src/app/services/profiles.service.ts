import {Injectable} from '@angular/core';
import {ApiResponseDto, ApiService} from './api.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProfilesService {

  constructor(private api: ApiService,
              private http: HttpClient) { }

  getProfiles() {
    return this.http.get<Profile[]>(this.api.profiles());
  }

  save(profile: Profile) {
    if (profile.id != null) {
      return this.http.put<ApiResponseDto>(this.api.profile(profile.id), profile);
    } else {
      return this.http.post<ApiResponseDto>(this.api.profiles(), profile);
    }
  }

  delete(profile: Profile) {
    return this.http.delete<ApiResponseDto>(this.api.profile(profile.id));
  }
}

export class Profile {
  id: number;
  name: string;
  level: number;
}
