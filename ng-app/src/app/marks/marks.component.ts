import {Component, OnInit} from '@angular/core';
import {Subject} from './model/subject';
import {ApiService} from '../shared/api.service';

@Component({
  selector: 'app-marks',
  templateUrl: './marks.component.html',
  styleUrls: ['./marks.component.css']
})
export class MarksComponent implements OnInit {
  subjects: Subject[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.getAllSubjects();
  }

  public getAllSubjects() {
    const url = 'http://localhost:8080/api/subjects/all';
    this.apiService.getAllSubjects().subscribe(
      res => {
        this.subjects = res;
      }, err => {
        alert('Wystąpił błąd');
      }
    );
  }

}
