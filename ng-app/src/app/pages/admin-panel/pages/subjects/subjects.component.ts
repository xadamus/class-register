import {Component, OnInit, ViewChild} from '@angular/core';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {AlertService} from '../../../../services/alert.service';
import {EditSettingsModel, GridComponent, SaveEventArgs, ToolbarItems} from '@syncfusion/ej2-angular-grids';
import {ClickEventArgs} from '@syncfusion/ej2-navigations';
import {Subject, SubjectsService} from '../../../../services/subjects.service';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.css']
})
export class SubjectsComponent implements OnInit {

  subjects: Subject[];
  editSettings: EditSettingsModel;
  toolbar: ToolbarItems[] | object;

  modalRef: BsModalRef;
  editedSubject: Subject;
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
      this.loadSubjects();
    },
    error: error => {
      this.alert.error(error.error.message);
      this.loadSubjects();
    }
  };

  constructor(private subjectsService: SubjectsService,
              private alert: AlertService,
              private modalService: BsModalService) {
  }

  ngOnInit() {
    this.loadSubjects();
    this.editSettings = {allowDeleting: true, showDeleteConfirmDialog: true};
    this.toolbar = [
      {text: 'Dodaj', prefixIcon: 'e-add', id: 'add'},
      {text: 'Edytuj', prefixIcon: 'e-edit', id: 'edit'},
      'Delete'
    ];
  }

  clickHandler(args: ClickEventArgs): void {
    if (args.item.id === 'add') {
      this.editSubject(new Subject());
    } else if (args.item.id === 'edit') {
      const selectedRecords = this.grid.getSelectedRecords();
      if (selectedRecords.length > 0) {
        this.editSubject(selectedRecords[0] as Subject);
      } else {
        this.alert.error('Nie zaznaczono żadnego przedmiotu.');
      }
    }
  }

  actionComplete(args: SaveEventArgs): void {
    if (args.requestType === 'delete') {
      this.subjectsService.delete(args.data[0]).subscribe(this.responseHandler);
    }
  }

  editSubject(subject: Subject) {
    this.editedSubject = subject;
    this.editing = subject.id != null;
    this.modalRef = this.modalService.show(this.template);
  }

  saveSubject(subject: Subject) {
    this.subjectsService.save(subject).subscribe(this.responseHandler);
    this.modalRef.hide();
  }

  private loadSubjects() {
    this.subjectsService.getSubjects().subscribe(subjects => {
      this.subjects = subjects;
    });
  }

}
