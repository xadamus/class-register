import {Component, OnInit, ViewChild} from '@angular/core';
import {AlertService} from '../../../../services/alert.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';
import {Profile, ProfilesService} from '../../../../services/profiles.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';

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
  editing: boolean;
  levels = [1, 2, 3, 4, 5, 6, 7, 8];

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
      this.loadProfiles();
    },
    error: error => {
      this.alert.error(error.error.message);
      this.loadProfiles();
    }
  };

  constructor(private alert: AlertService,
              private profilesService: ProfilesService,
              private modalService: BsModalService) { }

  ngOnInit() {
    this.loadProfiles();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [
      {text: 'Dodaj', prefixIcon: 'e-add', id: 'add'},
      {text: 'Edytuj', prefixIcon: 'e-edit', id: 'edit'},
      'Delete'
    ];
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'add') {
      this.editProfile(new Profile());
    } else if (args.item.id === 'edit') {
      const selectedRecords = this.grid.getSelectedRecords();
      if (selectedRecords.length > 0) {
        this.editProfile(selectedRecords[0] as Profile);
      } else {
        this.alert.error('Nie zaznaczono żadnej klasy.');
      }
    }
  }

  actionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.profilesService.delete(args.data[0]).subscribe(this.responseHandler);
    }
  }

  editProfile(profile: Profile) {
    this.editedProfile = profile;
    this.editing = profile.id != null;
    this.modalRef = this.modalService.show(this.template);
  }

  saveProfile(profile: Profile) {
    this.profilesService.save(profile).subscribe(this.responseHandler);
    this.modalRef.hide();
  }

  private loadProfiles() {
    this.profilesService.getProfiles().subscribe(profiles => {
      this.profiles = profiles;
    });
  }

}
