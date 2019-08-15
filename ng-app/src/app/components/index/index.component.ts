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
    } else {
      this.router.navigate(['/login']);
    }
  }

}
