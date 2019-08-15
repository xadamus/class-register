import {EventEmitter, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  receivedAlert = new EventEmitter<AlertMessage>();

  constructor() { }

  info(alertMessage: string): void {
    this.receivedAlert.emit({type: 'info', message: alertMessage});
  }

  error(alertMessage: string): void {
    this.receivedAlert.emit({type: 'danger', message: alertMessage});
  }
}

export class AlertMessage {
  type: string;
  message: string;
}
