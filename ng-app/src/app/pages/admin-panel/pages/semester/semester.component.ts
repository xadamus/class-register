import {Component, OnInit, ViewChild} from '@angular/core';
import {EditSettingsModel, GridComponent, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';
import {AlertService} from '../../../../services/alert.service';
import {Semester, SemesterService} from '../../../../services/semester.service';

@Component({
  selector: 'app-semester',
  templateUrl: './semester.component.html',
  styleUrls: ['./semester.component.css']
})
export class SemesterComponent implements OnInit {
  semesters: Semester[];
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
      this.loadSemesters();
    },
    error: error => {
      this.alert.error(error.error.message);
      this.loadSemesters();
    }
  };

  constructor(private alert: AlertService,
              private semesterService: SemesterService) { }

  ngOnInit() {
    this.loadSemesters();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [
      {text: 'Dodaj', prefixIcon: 'e-add', id: 'add'},
      {text: 'Ustaw jako aktywny', prefixIcon: 'e-edit', id: 'current'}
    ];
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'add') {
      this.semesterService.createSemester().subscribe(this.responseHandler);
    } else if (args.item.id === 'current') {
      const selectedRecords = this.grid.getSelectedRecords();
      if (selectedRecords.length > 0) {
        const semester = selectedRecords[0] as Semester;
        this.semesterService.setCurrentSemester(semester.id).subscribe(this.responseHandler);
      } else {
        this.alert.error('Nie zaznaczono żadnego semestru.');
      }
    }
  }

  private loadSemesters() {
    this.semesterService.getSemesters().subscribe(semesters => {
      this.semesters = semesters;
    });
  }
}
