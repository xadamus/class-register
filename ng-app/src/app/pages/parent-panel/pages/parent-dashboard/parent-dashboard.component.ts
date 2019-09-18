import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../../services/auth.service';

@Component({
  selector: 'app-parent-dashboard',
  templateUrl: './parent-dashboard.component.html',
  styleUrls: ['./parent-dashboard.component.css']
})
export class ParentDashboardComponent implements OnInit {

  constructor(private auth: AuthService) { }

  ngOnInit() {
  }

}
