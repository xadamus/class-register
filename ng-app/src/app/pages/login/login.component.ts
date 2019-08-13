import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../shared/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: LoginModel = {
    username: '',
    password: ''
  };

  constructor(private router: Router,
              private authService: AuthService) {
  }

  ngOnInit() {
  }

  login() {
    if (this.model.username && this.model.password) {
      this.authService.login(this.model.username, this.model.password).subscribe(() => {
        this.router.navigate(['']);
      }, error => {
        alert('błąd logowania');
      });
    }
  }

}

export interface LoginModel {
  username: string;
  password: string;
}
