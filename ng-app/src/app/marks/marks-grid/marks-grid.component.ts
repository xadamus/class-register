import {Component, Input, OnInit} from '@angular/core';
import {Mark} from '../marks.component';

@Component({
  selector: 'app-marks-grid',
  templateUrl: './marks-grid.component.html',
  styleUrls: ['./marks-grid.component.css']
})
export class MarksGridComponent implements OnInit {

  @Input() marks: Mark[] = [];

  constructor() { }

  ngOnInit() {
  }

}
