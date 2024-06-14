import { Component } from '@angular/core';
import { UserStatus, Usuario } from '@src/app/biz/biz-model';
import { AbstractCrudList } from '@src/app/core/service/crud/crud-impl.component';
import { MenuItem } from 'primeng/api';
import { UsuarioCrudService } from '../usuario.service';
import { UsuarioInfoComponent } from '../usuario-info/usuario-info.component';
import { DialogService } from 'primeng/dynamicdialog';
import { OpenMode } from '@src/app/core/service/crud/crud.service.obj';

@Component({
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
})
export class UsuarioListComponent extends AbstractCrudList<Usuario, UsuarioInfoComponent, UsuarioCrudService>{

  columns: any[] = [];
  contextMenuItens: MenuItem[] = [];
  actions: any[] = [];

  
  constructor(service:  UsuarioCrudService, viewer: UsuarioInfoComponent){
    super(service, viewer);
    
    this.columns = [
      { title: 'Nome', def: 'name', width: '50%', },
      { title: 'Username', def: 'username', width: '5%', },
      { title: 'Dt. Criação', def: 'createdAt', width: '15%', },
      { title: 'Dt. Últ. Alteração', def: 'updatedAt', width: '15%', },
      { title: 'Status', def: 'status', width: '*', fnElementClass: this.statusClass.bind(this), fnDataTransformation: this.statusLabelTransform.bind(this)},
      // { title: 'Observação', def: 'note', width: '15%', },
    ];

    
    this.contextMenuItens = [
      {label: 'Abrir', icon: 'pi pi-fw pi-user-edit', command: this.openToEdit.bind(this)},
      {separator: true},
      // {label: 'Remover', icon: 'pi pi-fw pi-trash', command: this.delete.bind(this)},
      {label: 'Bloquear / Desbloquear', icon: 'pi pi-fw pi-ban', command: this.toggleUserStatus.bind(this)},
    ];

  }

  statusClass(e: any): string{
    return UserStatus.fromName(e.status)?.cssClass;
  }
  statusLabelTransform(e: any){
    return UserStatus.fromName(e.status)?.description;
  }

  toggleUserStatus(i: any){
    this.service.toggleUserStatus(i.item?.element?.id);
  }

  override openItem(item: any, mode: OpenMode){
    // this._viewerComponent.openItem(item, mode);
  }



}
