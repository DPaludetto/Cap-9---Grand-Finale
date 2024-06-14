import { Component, Injectable } from '@angular/core';
import { ICrudViewComponent, OpenMode } from '@src/app/core/service/crud/crud.service.obj';
import { DialogService } from 'primeng/dynamicdialog';

@Injectable({providedIn: 'root'})
@Component({
  selector: 'app-usuario-info',
  templateUrl: './usuario-info.component.html',
  styleUrls: ['./usuario-info.component.scss'],

})
export class UsuarioInfoComponent implements ICrudViewComponent{


  constructor(private modal: DialogService){}

  openItem(item: any, mode: OpenMode): void {
    this.modal.open(UsuarioInfoComponent, {
      header: `Usuário`,
      width: '60%',
      height: '90%',
      contentStyle: { overflow: 'auto' },
      baseZIndex: 10000,
      maximizable: true,
      data: {
        item: item
      },
    });
  }

}
