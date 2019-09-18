import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from '../../../../services/auth.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {UsersService} from '../../../../services/users.service';
import {AlertService} from '../../../../services/alert.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';
import {Student, StudentsService} from '../../../../services/students.service';

@Component({
  selector: 'app-student-management',
  templateUrl: './student-management.component.html',
  styleUrls: ['./student-management.component.css']
})
export class StudentManagementComponent implements OnInit {
  students: Student[];
  editSettings: EditSettingsModel;
  toolbar: ToolbarItems[] | object;

  modalRef: BsModalRef;
  editedStudent: Student;
  editing: boolean;
  freeUsers: User[];
  freeParents: User[];

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
      this.loadStudents();
    },
    error: error => {
      this.alert.error(error.error.message);
      this.loadStudents();
    }
  };

  constructor(private studentsService: StudentsService,
              private usersService: UsersService,
              private alert: AlertService,
              private modalService: BsModalService) {
  }

  ngOnInit() {
    this.loadStudents();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [
      {text: 'Dodaj', prefixIcon: 'e-add', id: 'add'},
      {text: 'Edytuj', prefixIcon: 'e-edit', id: 'edit'},
      'Delete'
    ];
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'add') {
      this.editStudent(new Student());
    } else if (args.item.id === 'edit') {
      const selectedRecords = this.grid.getSelectedRecords();
      if (selectedRecords.length > 0) {
        this.editStudent(selectedRecords[0] as Student);
      } else {
        this.alert.error('Nie zaznaczono żadnego ucznia.');
      }
    }
  }

  actionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.studentsService.delete(args.data[0]).subscribe(this.responseHandler);
    }
  }

  editStudent(student: Student) {
    this.usersService.getFreeUsers().subscribe(value => {
        this.editedStudent = student;
        this.editing = student.id != null;
        this.modalRef = this.modalService.show(this.template);

        this.freeUsers = value.slice();
        if (student.userId != null) {
          const user = new User();
          user.id = student.userId;
          user.username = student.username;
          this.freeUsers.push(user);
        }
        this.freeUsers.push(new User());

        this.freeParents = value.slice();
        this.freeParents.push(new User());
    });
  }

  saveStudent(student: Student) {
    this.studentsService.save(student).subscribe(this.responseHandler);
    this.modalRef.hide();
  }

  private loadStudents() {
    this.studentsService.getStudents().subscribe(students => {
      this.students = students;
    });
  }
}
