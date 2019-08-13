import {Component, OnInit} from '@angular/core';
import {Subject} from './model/subject';
import {ApiService} from '../../shared/api.service';

@Component({
  selector: 'app-marks',
  templateUrl: './marks.component.html',
  styleUrls: ['./marks.component.css']
})
export class MarksComponent implements OnInit {
  subjects: Subject[] = [];
  selectedSubject: Subject = null;
  marks: Mark[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.getAllSubjects();
  }

  public getAllSubjects() {
    this.apiService.getAllSubjects().subscribe(
      res => {
        this.subjects = res;
      }, err => {
        console.log('brak przedmiotów');
        console.log(err);
      }
    );
  }

  selectSubject(subject: Subject) {
    this.selectedSubject = subject;
    this.marks = subject.marks;
  }
}

export interface Mark {
  id: string;
  value: number;
  subject: Subject;
}
