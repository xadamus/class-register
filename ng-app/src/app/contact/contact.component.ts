import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

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

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  sendMessage(): void {
    const url = 'http://localhost:8080/api/contact';
    this.http.post(url, this.model).subscribe(
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
