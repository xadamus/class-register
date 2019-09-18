import {AfterViewChecked, Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-index',
  template: ''
})
export class IndexComponent implements OnInit, AfterViewChecked {

  constructor(private auth: AuthService,
              private router: Router) { }

  ngOnInit() {
  }

  ngAfterViewChecked() {
    if (this.auth.hasRole('ROLE_ADMIN')) {
      this.router.navigate(['/admin']);
    } else if (this.auth.hasRole('ROLE_TEACHER')) {
      this.router.navigate(['/teacher']);
    } else if (this.auth.hasRole('ROLE_PARENT')) {
      this.router.navigate(['/parent']);
    } else if (this.auth.hasRole('ROLE_STUDENT')) {
      this.router.navigate(['/student']);
    } else {
      this.router.navigate(['/login']);
    }
  }

}
