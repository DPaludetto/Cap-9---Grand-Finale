import { Injectable } from '@angular/core';
import { AppComponent } from '@src/app/app.component';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  toasts: any[] = [];
  static DELAY_SHOW_MESSAGE = 5000; //time to show message

  constructor(
    // private service: MessageService
    ){}

  remove(toast: any): void {
    this.toasts = this.toasts.filter((t) => t !== toast);
  }

  showStandard(message: string): void {
    this.show(message, {
      iconName: 'check',
      classname: 'bg-standard',
      delay: ToastService.DELAY_SHOW_MESSAGE,
      errorMsg: true,
    });

  }

  showSuccess(message: string): void {
    this.show(message, {
      iconName: 'check',
      classname: 'bg-success text-light',
      delay: ToastService.DELAY_SHOW_MESSAGE,
      errorMsg: true,
    });
  }

  showDanger(message: string): void {
    // this.service.add({ key: 'tst', severity: 'error', summary: 'Erro', detail: message });
    this.show(message, {
      iconName: 'circleX',
      classname: 'bg-danger text-light',
      delay: ToastService.DELAY_SHOW_MESSAGE,
      errorMsg: true,
    });
  }

  verifyMsg(): boolean {
    return this.toasts.includes((toast: any) => toast.errorMsg);
  }

  private show(textOrTpl: string, options: any = {}): void {
    if (this.toasts.length === 0) {
      this.toasts.push({ textOrTpl, ...options });
    } else {
      this.toasts.pop();
      this.toasts.push({ textOrTpl, ...options });
    }
  }
}
