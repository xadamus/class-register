import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../shared/auth.service';
import {AlertService} from '../../shared/alert.service';

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
              private authService: AuthService,
              private alert: AlertService) {
  }

  ngOnInit() {
  }

  login() {
    if (this.model.username && this.model.password) {
      this.authService.login(this.model.username, this.model.password).subscribe(() => {
        this.router.navigate(['']);
      }, error => {
        this.alert.error('Podano nieprawidłowy login lub hasło.');
      });
    }
  }

}

export interface LoginModel {
  username: string;
  password: string;
}
