import {Component, OnInit} from '@angular/core';
import {User} from '../../../../services/auth.service';
import {UsersService} from '../../../../services/users.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  users: User[];

  constructor(private usersService: UsersService) { }

  ngOnInit() {
   this.usersService.getUsers().subscribe(users => {
     this.users = users;
   });
  }

}
