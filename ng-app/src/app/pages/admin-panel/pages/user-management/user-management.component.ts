import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from '../../../../services/auth.service';
import {UsersService} from '../../../../services/users.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';
import {AlertService} from '../../../../services/alert.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  users: User[];
  editSettings: EditSettingsModel;
  toolbar: ToolbarItems[] | object;

  @ViewChild('grid')
  public grid: GridComponent;

  private responseHandler = {
    next: response => {
      if (response.success) {
        this.alert.info(response.message);
      } else {
        this.alert.error(response.message);
      }
      this.loadUsers();
    },
    error: error => {
      this.alert.error(error.error.message);
      this.loadUsers();
    }
  };

  constructor(private usersService: UsersService,
              private alert: AlertService) {
  }

  ngOnInit() {
    this.loadUsers();
    this.editSettings = {showDeleteConfirmDialog: true, allowEditing: true, allowAdding: true, allowDeleting: true, mode: 'Dialog'};
    this.toolbar = ['Add', 'Edit', 'Delete',
      {text: 'Zaloguj jako', tooltipText: 'Zaloguj się do systemu jako wybrany użytkownik.', prefixIcon: 'fa fa-user', id: 'loginAs'}];
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'loginAs') {
      this.alert.info('Logowanie jako wybrany użytkownik powiodło się.');
    }
  }

  actionBegin(args: SaveEventArgs) {
    console.log(args);
  }

  actionComplete(args: SaveEventArgs): void {
    console.log(args);
    if (args.requestType === 'save') {
      this.usersService.save(args.data).subscribe(this.responseHandler);
    } else if (args.requestType === 'delete') {
      this.usersService.delete(args.data[0]).subscribe(this.responseHandler);
    }
  }

  private loadUsers() {
    this.usersService.getUsers().subscribe(users => {
      this.users = users;
    });
  }

}
