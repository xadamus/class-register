import {Component, OnInit} from '@angular/core';
import {StudentsService, SubjectMarks} from '../../../../services/students.service';
import {SubjectsService} from '../../../../services/subjects.service';
import {AuthService} from '../../../../services/auth.service';

@Component({
  selector: 'app-parent-marks',
  templateUrl: './parent-marks.component.html',
  styleUrls: ['./parent-marks.component.css']
})
export class ParentMarksComponent implements OnInit {

  subjectMarksList: SubjectMarks[];

  constructor(private studentService: StudentsService,
              private subjectService: SubjectsService,
              private auth: AuthService) {
  }

  ngOnInit() {
    this.loadStudentMarks();
  }

  private loadStudentMarks() {
    this.studentService.getStudentMarks(this.auth.getCurrentUser().child.id).subscribe(subjectMarksList => {
      subjectMarksList.forEach(subjectMarks => {
        subjectMarks.marksString = subjectMarks.marks.map(m => m.value).join(', ');
      });
      this.subjectMarksList = subjectMarksList;
    });
  }

}
