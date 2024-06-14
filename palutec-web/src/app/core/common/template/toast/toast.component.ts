import { Component } from '@angular/core';
import { ToastService } from './toast.service';



@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',

})
export class ToastComponent {
  constructor(readonly toastService: ToastService) {}
}
