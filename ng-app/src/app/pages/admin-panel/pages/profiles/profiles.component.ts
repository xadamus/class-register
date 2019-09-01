import {Component, OnInit, ViewChild} from '@angular/core';
import {AlertService} from '../../../../services/alert.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';
import {Allocation, AllocationCreationDto, Profile, ProfilesService} from '../../../../services/profiles.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {Semester, SemesterService} from '../../../../services/semester.service';
import {Teacher, TeachersService} from '../../../../services/teachers.service';
import {Subject, SubjectsService} from '../../../../services/subjects.service';

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

  allocationToolbar: ToolbarItems[] | object;
  allocations: Allocation[];
  newAllocation: AllocationCreationDto;
  semesters: Semester[];
  teachers: Teacher[];
  subjects: Subject[];

  @ViewChild('grid')
  public grid: GridComponent;

  @ViewChild('editProfileTemplate')
  public editProfileTemplate;

  @ViewChild('allocationGrid')
  public allocationGrid: GridComponent;

  @ViewChild('editAllocationTemplate')
  public editAllocationTemplate;

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
              private subjectService: SubjectsService) { }

  ngOnInit() {
    this.loadProfiles();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [
      {text: 'Dodaj', prefixIcon: 'e-add', id: 'add'},
      {text: 'Edytuj', prefixIcon: 'e-edit', id: 'edit'},
      'Delete',
      {text: 'Przydziały', prefixIcon: 'e-columnchooser', id: 'allocation'}
    ];
    this.allocationToolbar = [
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
    this.modalRef = this.modalService.show(this.editAllocationTemplate);
  }

  saveProfile(profile: Profile) {
    this.profilesService.saveProfile(profile).subscribe(this.responseHandler);
    this.modalRef.hide();
  }

  allocationActionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.profilesService.deleteProfileAllocation(args.data[0]).subscribe(this.responseHandler);
    }
  }

  createAllocation() {
    this.profilesService.createProfileAllocation(this.newAllocation).subscribe(this.responseHandler);
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
}
