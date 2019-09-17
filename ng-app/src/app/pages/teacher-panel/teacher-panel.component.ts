import {Component, OnInit} from '@angular/core';
import {Semester, SemesterService} from '../../services/semester.service';
import {Allocation, ProfilesService} from '../../services/profiles.service';
import {AuthService} from '../../services/auth.service';
import {TeachersService} from '../../services/teachers.service';

@Component({
  selector: 'app-teacher-panel',
  templateUrl: './teacher-panel.component.html',
  styleUrls: ['./teacher-panel.component.css']
})
export class TeacherPanelComponent implements OnInit {
  semester: Semester;
  allocation: Allocation;
  availableAllocations: Allocation[] = [];

  constructor(private semesterService: SemesterService,
              private profileService: ProfilesService,
              private auth: AuthService,
              private teacherService: TeachersService) { }

  ngOnInit() {
    this.semesterService.getCurrentSemester().subscribe(semester => {
      this.semester = semester;
      this.profileService.getTeacherProfileAllocations(this.auth.getCurrentUser().teacher, this.semester).subscribe(allocations => {
        this.availableAllocations = allocations;
      });
    });
  }

  chooseAllocation(allocation: Allocation) {
    this.allocation = allocation;
    this.teacherService.setCurrentAllocation(allocation);
  }
}
