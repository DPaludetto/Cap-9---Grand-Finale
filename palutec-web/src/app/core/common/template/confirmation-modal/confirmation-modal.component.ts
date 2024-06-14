import { Component, Injectable, Input } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-confirmation-modal',
  templateUrl: './confirmation-modal.component.html',
})
export class ConfirmationModalComponent {
  @Input() modal: any;

  @Input() message = '';

  @Input() title = '';

  @Input() icon = '';

  displayModal = true;
}
