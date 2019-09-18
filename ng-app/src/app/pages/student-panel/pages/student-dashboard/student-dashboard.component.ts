import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../../services/auth.service';

@Component({
  selector: 'app-student-dashboard',
  templateUrl: './student-dashboard.component.html',
  styleUrls: ['./student-dashboard.component.css']
})
export class StudentDashboardComponent implements OnInit {

  constructor(private auth: AuthService) { }

  ngOnInit() {
  }

}
