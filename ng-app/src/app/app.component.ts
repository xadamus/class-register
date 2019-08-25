import {Component} from '@angular/core';
import {L10n, setCulture} from '@syncfusion/ej2-base';

setCulture('pl-PL');
L10n.load({
  'pl-PL': {
    grid: {
      EmptyRecord: 'Brak rekordów do wyświetlenia',
      Add: 'Dodaj',
      Edit: 'Edytuj',
      Delete: 'Usuń',
      Update: 'Aktualizuj',
      Cancel: 'Anuluj',
      EditFormTitle: 'Edycja ',
      SaveButton: 'Zapisz',
      CancelButton: 'Anuluj',
      ConfirmDelete: 'Czy na pewno chcesz usunąć?',
      DeleteOperationAlert: 'Nie zaznaczono żadnego rekordu'
    }
  }
});

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Class Register';
}
