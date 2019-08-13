import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {AlertService} from '../../shared/alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
  animations: [
    trigger('openClose', [
      state('open', style({
        opacity: 1
      })),
      state('closed', style({
        opacity: 0
      })),
      transition('open => closed', [
        animate('0.5s')
      ]),
      transition('closed => open', [
        animate('0.5s')
      ]),
    ]),
  ]
})
export class AlertComponent implements OnInit {
  visible: boolean;
  message: string;
  type = 'info';

  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.alertService.receivedAlert.subscribe(alert => {
      this.message = alert.message;
      this.type = alert.type;
      this.open();
    });
  }

  close() {
    this.visible = false;
  }

  open() {
    this.visible = true;
  }

}
