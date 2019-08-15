import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {AlertService} from '../../services/alert.service';
import {timer} from 'rxjs';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
  animations: [
    trigger('openClose', [
      state('open', style({
        opacity: 1,
        visibility: 'visible'
      })),
      state('closed', style({
        opacity: 0,
        visibility: 'hidden'
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
  private closeTimer = timer(5000);

  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.alertService.receivedAlert.subscribe(alert => {
      this.message = alert.message;
      this.type = alert.type;
      this.open();
      this.closeTimer.subscribe(() => {
        this.close();
      });
    });
  }

  close() {
    this.visible = false;
  }

  open() {
    this.visible = true;
  }

}
