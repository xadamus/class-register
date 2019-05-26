import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Subject} from './model/subject';

@Component({
  selector: 'app-marks',
  templateUrl: './marks.component.html',
  styleUrls: ['./marks.component.css']
})
export class MarksComponent implements OnInit {
  subjects: Subject[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.getAllSubjects();
  }

  public getAllSubjects() {
    const url = 'http://localhost:8080/api/subjects/all';
    this.http.get<Subject[]>(url).subscribe(
      res => {
        this.subjects = res;
      }, err => {
        alert('Wystąpił błąd');
      }
    );
  }

}
