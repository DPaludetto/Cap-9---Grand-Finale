
import { Router } from '@angular/router';

import { Component, ViewChild } from '@angular/core';
import { SimpleCrudTemplateComponent } from './simple-crud-template/simple-crud-template.component';
import { ToastService } from './toast/toast.service';
import { ICrudService } from '../../service/crud/crud.service.obj';

@Component({
  template: '',
})
export abstract class CrudBaseComponent<T, ID> {
  crudViewMode = '';

  constructor(
    protected router: Router,
    protected toastService: ToastService,
  ) { }

  abstract get crudService(): ICrudService<T, ID>;

  abstract get value(): T;

  abstract get id(): ID;

  abstract get baseCrudUrl(): string;

  @ViewChild(SimpleCrudTemplateComponent)
  set crudMode(crudTemplate: SimpleCrudTemplateComponent) {
    this.crudViewMode = crudTemplate.mode;
  }

  create(): void {
    this.beforeSave();
    if (this.isValidToSave) {
      this.crudService.create(this.value).subscribe((response) => {
        this.afterSave(response);
        this.detail();
        this.toastService.showSuccess(this.createMessage);
      });
    }
  }

  remove(): void {
    if (this.id) {
      this.crudService.delete(this.id).subscribe(() => {
        this.back();
        this.toastService.showSuccess(this.removeMessage);
      });
    }
  }

  save(): void {
    this.beforeSave();
    if (this.isValidToSave) {
      this.crudService.update(this.value).subscribe((response) => {
        this.afterSave(response);
        this.detail();
        this.toastService.showSuccess('Alterações salvas com sucesso!');
      });
    }
  }

  detail(): void {
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([`${this.baseCrudUrl}/detail/${this.id}`, { mode: 'DETAIL' }]);
    });
  }

  back(): void {
    this.router.navigateByUrl(this.baseCrudUrl);
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected beforeSave(): void {
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected afterSave(newValue: T): void {
  }

  protected get isValidToSave(): boolean {
    return true;
  }

  get viewMode(): string {
    return this.crudViewMode;
  }

  get isCreateMode(): boolean {
    return this.viewMode === 'CREATE';
  }

  get createMessage(): string {
    return 'Salvo com sucesso!';
  }

  get removeMessage(): string {
    return 'Excluído com sucesso!';
  }
}
