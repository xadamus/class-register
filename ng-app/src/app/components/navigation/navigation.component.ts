import {Component, OnInit} from '@angular/core';
import {AuthService, User} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor(private auth: AuthService,
              private router: Router) { }

  authenticated: boolean;
  currentUser: User;

  ngOnInit() {
    this.getAuthentication();

    this.auth.change.subscribe(() => {
      this.getAuthentication();
    });
  }

  logout() {
    this.auth.logout();
    this.authenticated = this.auth.authenticated();
    this.currentUser = this.auth.getCurrentUser();
    this.router.navigate(['/login']);
  }

  private getAuthentication() {
    this.authenticated = this.auth.authenticated();
    this.currentUser = this.auth.getCurrentUser();
  }
}
