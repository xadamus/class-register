import {Component, OnInit, ViewChild} from '@angular/core';
import {AlertService} from '../../../../services/alert.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';
import {
  Allocation,
  AllocationCreationDto,
  Membership,
  MembershipCreationDto,
  Profile,
  ProfilesService
} from '../../../../services/profiles.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {Semester, SemesterService} from '../../../../services/semester.service';
import {Teacher, TeachersService} from '../../../../services/teachers.service';
import {Subject, SubjectsService} from '../../../../services/subjects.service';
import {Student, StudentsService} from '../../../../services/students.service';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit {
  profiles: Profile[];
  editSettings: EditSettingsModel;
  toolbar: ToolbarItems[] | object;

  modalRef: BsModalRef;
  editedProfile: Profile;
  editingProfile: boolean;
  levels = [1, 2, 3, 4, 5, 6, 7, 8];

  deleteToolbar: ToolbarItems[] | object;
  allocations: Allocation[];
  newAllocation: AllocationCreationDto;
  semesters: Semester[];
  teachers: Teacher[];
  subjects: Subject[];

  memberships: Membership[];
  newMembership: MembershipCreationDto;
  students: Student[];

  @ViewChild('grid')
  public grid: GridComponent;

  @ViewChild('editProfileTemplate')
  public editProfileTemplate;

  @ViewChild('allocationGrid')
  public allocationGrid: GridComponent;

  @ViewChild('editAllocationTemplate')
  public editAllocationTemplate;

  @ViewChild('membershipGrid')
  public membershipGrid: GridComponent;

  @ViewChild('editMembershipTemplate')
  public editMembershipTemplate;

  private responseHandler = {
    next: response => {
      if (response.success) {
        this.alert.info(response.message);
      } else {
        this.alert.error(response.message);
      }
      this.loadProfiles();
      if (this.editedProfile != null) {
        this.loadAllocations(this.editedProfile);
        this.loadMemberships(this.editedProfile);
      }
    },
    error: error => {
      this.alert.error(error.error.message);
      this.loadProfiles();
    }
  };

  constructor(private alert: AlertService,
              private profilesService: ProfilesService,
              private modalService: BsModalService,
              private semesterService: SemesterService,
              private teacherService: TeachersService,
              private studentService: StudentsService,
              private subjectService: SubjectsService) { }

  ngOnInit() {
    this.loadProfiles();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [
      {text: 'Dodaj', prefixIcon: 'e-add', id: 'add'},
      {text: 'Edytuj', prefixIcon: 'e-edit', id: 'edit'},
      'Delete',
      {text: 'Nauczyciele', prefixIcon: 'e-columnchooser', id: 'allocation'},
      {text: 'Uczniowie', prefixIcon: 'e-icon-rowselect', id: 'membership'}
    ];
    this.deleteToolbar = [
      'Delete'
    ];
  }

  profileHandler(args: ClickEventArgs): void {
    const selectedRecords = this.grid.getSelectedRecords();
    if (args.item.id === 'add') {
      this.editProfile(new Profile());
    } else if (args.item.id === 'edit') {
      if (selectedRecords.length > 0) {
        this.editProfile(selectedRecords[0] as Profile);
      } else {
        this.alert.error('Nie zaznaczono żadnej klasy.');
      }
    } else if (args.item.id === 'allocation') {
      if (selectedRecords.length > 0) {
        this.editAllocation(selectedRecords[0] as Profile);
      } else {
        this.alert.error('Nie zaznaczono żadnej klasy.');
      }
    } else if (args.item.id === 'membership') {
      if (selectedRecords.length > 0) {
        this.editMembership(selectedRecords[0] as Profile);
      } else {
        this.alert.error('Nie zaznaczono żadnej klasy.');
      }
    }
  }

  profileActionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.profilesService.deleteProfile(args.data[0]).subscribe(this.responseHandler);
    }
  }

  editProfile(profile: Profile) {
    this.editedProfile = profile;
    this.editingProfile = profile.id != null;
    this.modalRef = this.modalService.show(this.editProfileTemplate);
  }

  editAllocation(profile: Profile) {
    this.loadAllocations(profile);
    this.editedProfile = profile;
    this.newAllocation = new AllocationCreationDto();
    this.newAllocation.profileId = profile.id;
    this.semesterService.getSemesters().subscribe(values => {
      this.semesters = values;
    });
    this.teacherService.getTeachers().subscribe(values => {
      this.teachers = values;
    });
    this.subjectService.getSubjects().subscribe(values => {
      this.subjects = values;
    });
    this.modalRef = this.modalService.show(this.editAllocationTemplate, {class: 'modal-lg'});
  }

  saveProfile(profile: Profile) {
    this.profilesService.saveProfile(profile).subscribe(this.responseHandler);
    this.finishEditing();
  }

  editMembership(profile: Profile) {
    this.loadMemberships(profile);
    this.editedProfile = profile;
    this.newMembership = new MembershipCreationDto();
    this.newMembership.profileId = profile.id;
    this.semesterService.getSemesters().subscribe(values => {
      this.semesters = values;
    });
    this.studentService.getStudents().subscribe(values => {
      this.students = values;
    });
    this.modalRef = this.modalService.show(this.editMembershipTemplate, {class: 'modal-lg'});
  }

  allocationActionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.profilesService.deleteProfileAllocation(args.data[0]).subscribe(this.responseHandler);
    }
  }

  createAllocation() {
    this.profilesService.createProfileAllocation(this.newAllocation).subscribe(this.responseHandler);
  }

  membershipActionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.profilesService.deleteProfileMembership(args.data[0]).subscribe(this.responseHandler);
    }
  }

  createMembership() {
    this.profilesService.createProfileMembership(this.newMembership).subscribe(this.responseHandler);
  }

  finishEditing() {
    this.modalRef.hide();
    this.editedProfile = null;
    this.newAllocation = null;
    this.newMembership = null;
    this.semesters = null;
    this.students = null;
    this.teachers = null;
    this.subjects = null;
  }

  private loadProfiles() {
    this.profilesService.getProfiles().subscribe(profiles => {
      this.profiles = profiles;
    });
  }

  private loadAllocations(profile: Profile) {
    this.profilesService.getProfileAllocations(profile).subscribe(result => {
      this.allocations = result;
    });
  }

  private loadMemberships(profile: Profile) {
    this.profilesService.getProfileMemberships(profile).subscribe(result => {
      this.memberships = result;
    });
  }
}
