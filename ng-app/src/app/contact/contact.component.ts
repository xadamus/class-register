import {Component, OnInit} from '@angular/core';
import {ApiService} from '../shared/api.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  model: ContactViewModel = {
    name: '',
    email: '',
    message: ''
  };

  constructor(private apiService: ApiService) {
  }

  ngOnInit() {
  }

  sendMessage(): void {
    this.apiService.sendMessage(this.model).subscribe(
      res => {
        location.reload();
      }, err => {
        alert('Wystąpił błąd');
      }
    );
  }
}

export interface ContactViewModel {
  name: string;
  email: string;
  message: string;
}
