import {Component, OnInit, ViewChild} from '@angular/core';
import {TeachersService} from '../../../../services/teachers.service';
import {AlertService} from '../../../../services/alert.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {Mark, Membership, ProfilesService} from '../../../../services/profiles.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';

@Component({
  selector: 'app-teacher-marks',
  templateUrl: './teacher-marks.component.html',
  styleUrls: ['./teacher-marks.component.css']
})
export class TeacherMarksComponent implements OnInit {
  editSettings: EditSettingsModel;
  toolbar: ToolbarItems[] | object;
  deleteToolbar: ToolbarItems[] | object;

  modalRef: BsModalRef;
  editedMembership: Membership;
  memberships: Membership[];

  marks: Mark[];
  newMark: Mark;

  @ViewChild('grid')
  public grid: GridComponent;

  @ViewChild('editMarksTemplate')
  public editMarksTemplate;

  @ViewChild('marksGrid')
  public marksGrid: GridComponent;

  private responseHandler = {
    next: response => {
      if (response.success) {
        this.alert.info(response.message);
      } else {
        this.alert.error(response.message);
      }
      this.loadMemberships();
      if (this.editedMembership != null) {
        this.loadMarks();
      }
    },
    error: error => {
      this.alert.error(error.error.message);
      this.loadMemberships();
    }
  };

  constructor(private teacherService: TeachersService,
              private alert: AlertService,
              private profileService: ProfilesService,
              private modalService: BsModalService) { }

  ngOnInit() {
    this.loadMemberships();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [{text: 'Edytuj oceny', prefixIcon: 'e-columnchooser', id: 'marks'}];
    this.deleteToolbar = [
      'Delete'
    ];
  }

  private loadMemberships() {
    this.profileService.getProfileMemberships(this.teacherService.getCurrentAllocation().profile).subscribe(memberships => {
      this.memberships = memberships;
    });
  }

  private loadMarks() {
    this.profileService.getMarks(this.editedMembership.id, this.teacherService.getCurrentAllocation().subject.id)
      .subscribe(marks => {
        this.marks = marks;
      });
  }

  finishEditing() {
    this.modalRef.hide();
  }

  membershipHandler(args: ClickEventArgs) {
    const selectedRecords = this.grid.getSelectedRecords();
    if (args.item.id === 'marks') {
      this.editMembership(selectedRecords[0] as Membership);
    }
  }

  addMark() {
    this.profileService.addMark(this.editedMembership.id,
      this.teacherService.getCurrentAllocation().subject.id, this.newMark).subscribe(this.responseHandler);
  }

  marksActionComplete(args: SaveEventArgs) {
    if (args.requestType === 'delete') {
      this.profileService.deleteMark(this.editedMembership.id,
        this.teacherService.getCurrentAllocation().subject.id, args.data[0])
        .subscribe(this.responseHandler);
    }
  }

  private editMembership(membership: Membership) {
    this.editedMembership = membership;
    this.modalRef = this.modalService.show(this.editMarksTemplate);
    this.newMark = new Mark();
    this.loadMarks();
  }
}
