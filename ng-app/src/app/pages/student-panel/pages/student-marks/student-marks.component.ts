import {Component, OnInit} from '@angular/core';
import {StudentsService, SubjectMarks} from '../../../../services/students.service';
import {AuthService} from '../../../../services/auth.service';
import {SubjectsService} from '../../../../services/subjects.service';

@Component({
  selector: 'app-student-marks',
  templateUrl: './student-marks.component.html',
  styleUrls: ['./student-marks.component.css']
})
export class StudentMarksComponent implements OnInit {
  subjectMarksList: SubjectMarks[];

  constructor(private studentService: StudentsService,
              private subjectService: SubjectsService,
              private auth: AuthService) { }

  ngOnInit() {
    this.loadStudentMarks();
  }

  private loadStudentMarks() {
    this.studentService.getStudentMarks(this.auth.getCurrentUser().student.id).subscribe(subjectMarksList => {
      subjectMarksList.forEach(subjectMarks => {
        subjectMarks.marksString = subjectMarks.marks.map(m => m.value).join(', ');
      });
      this.subjectMarksList = subjectMarksList;
    });
  }

}
