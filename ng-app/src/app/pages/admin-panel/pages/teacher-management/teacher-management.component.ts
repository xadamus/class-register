import {Component, OnInit, ViewChild} from '@angular/core';
import {Teacher, TeachersService} from '../../../../services/teachers.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {User} from '../../../../services/auth.service';
import {UsersService} from '../../../../services/users.service';
import {AlertService} from '../../../../services/alert.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';

@Component({
  selector: 'app-teacher-management',
  templateUrl: './teacher-management.component.html',
  styleUrls: ['./teacher-management.component.css']
})
export class TeacherManagementComponent implements OnInit {
  teachers: Teacher[];
  editSettings: EditSettingsModel;
  toolbar: ToolbarItems[] | object;

  modalRef: BsModalRef;
  editedTeacher: Teacher;
  editing: boolean;
  freeUsers: User[];

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
      this.loadTeachers();
    },
    error: error => {
      this.alert.error(error.error.message);
      this.loadTeachers();
    }
  };

  constructor(private teachersService: TeachersService,
              private usersService: UsersService,
              private alert: AlertService,
              private modalService: BsModalService) {
  }

  ngOnInit() {
    this.loadTeachers();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [
      {text: 'Dodaj', prefixIcon: 'e-add', id: 'add'},
      {text: 'Edytuj', prefixIcon: 'e-edit', id: 'edit'},
      'Delete'
    ];
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'add') {
      this.editTeacher(new Teacher());
    } else if (args.item.id === 'edit') {
      const selectedRecords = this.grid.getSelectedRecords();
      if (selectedRecords.length > 0) {
        this.editTeacher(selectedRecords[0] as Teacher);
      } else {
        this.alert.error('Nie zaznaczono żadnego nauczyciela.');
      }
    }
  }

  actionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.teachersService.delete(args.data[0]).subscribe(this.responseHandler);
    }
  }

  editTeacher(teacher: Teacher) {
    this.usersService.getFreeUsers().subscribe(value => {
      this.freeUsers = value;
      this.freeUsers.push(new User());
      if (teacher.userId != null) {
        const user = new User();
        user.id = teacher.userId;
        user.username = teacher.username;
        this.freeUsers.push(user);
      }

      this.editedTeacher = teacher;
      this.editing = teacher.id != null;
      this.modalRef = this.modalService.show(this.template);
    });
  }

  saveTeacher(teacher: Teacher) {
    this.teachersService.save(teacher).subscribe(this.responseHandler);
    this.modalRef.hide();
  }

  private loadTeachers() {
    this.teachersService.getTeachers().subscribe(teachers => {
      this.teachers = teachers;
    });
  }
}
