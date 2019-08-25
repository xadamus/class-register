import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from '../../../../services/auth.service';
import {UsersService} from '../../../../services/users.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';
import {AlertService} from '../../../../services/alert.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  users: User[];
  editSettings: EditSettingsModel;
  toolbar: ToolbarItems[] | object;

  modalRef: BsModalRef;
  editedUser: User;
  editing: boolean;

  @ViewChild('grid')
  public grid: GridComponent;

  @ViewChild('template')
  public template;

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
              private alert: AlertService,
              private modalService: BsModalService) {
  }

  ngOnInit() {
    this.loadUsers();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [
      {text: 'Dodaj', prefixIcon: 'e-add', id: 'add'},
      {text: 'Edytuj', prefixIcon: 'e-edit', id: 'edit'},
      'Delete'
    ];
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'add') {
      this.editUser(new User());
    } else if (args.item.id === 'edit') {
      const selectedRecords = this.grid.getSelectedRecords();
      if (selectedRecords.length > 0) {
        this.editUser(selectedRecords[0] as User);
      } else {
        this.alert.error('Nie zaznaczono żadnego użytkownika.');
      }
    }
  }

  actionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.usersService.delete(args.data[0]).subscribe(this.responseHandler);
    }
  }

  editUser(user: User) {
    this.modalRef = this.modalService.show(this.template);
    this.editedUser = user;
    this.editing = user.id != null;
  }

  saveUser(user: User) {
    this.usersService.save(user).subscribe(this.responseHandler);
    this.modalRef.hide();
  }

  updateRole(role: string, event) {
    if (event.target.checked) {
      this.editedUser.roles.push(role);
    } else {
      this.editedUser.roles.splice(this.editedUser.roles.indexOf(role), 1);
    }
  }

  private loadUsers() {
    this.usersService.getUsers().subscribe(users => {
      this.users = users;
    });
  }
}
