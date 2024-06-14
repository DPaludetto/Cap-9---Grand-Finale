import { Component } from '@angular/core';
import { GrupoResolucao} from '@src/app/biz/biz-model';
import { AbstractCrudList} from '@src/app/core/service/crud/crud-impl.component';
import { GrupoResolucaoCrudService } from '../grupoResolucao-service';
import { MenuItem } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { GrupoResolucaoInfoComponent } from '../grupos-info/grupos-info.component';
import { OpenMode } from '@src/app/core/service/crud/crud.service.obj';

@Component({
  selector: 'app-grupos-list',
  templateUrl: './grupos-list.component.html',
  styleUrls: ['./grupos-list.component.scss'],
  providers: [
    DialogService
  ]
})
export class GrupoResolucaoListComponent extends AbstractCrudList<GrupoResolucao, GrupoResolucaoInfoComponent, GrupoResolucaoCrudService>{

  columns: any[] = [];
  contextMenuItens: MenuItem[] = [];
  actions: any[] = [];

  constructor(
      private serviceInstance: GrupoResolucaoCrudService,
      private viewer: GrupoResolucaoInfoComponent){

    super(serviceInstance, viewer);

    this.columns = [
      { title: 'Nome', def: 'nome', width: '20%', },
      { title: 'Descrição', def: 'descricao', width: '*', },
      { title: 'Dt. Criação', def: 'createdAt', width: '220px', },
      { title: 'Dt. Últ. Alteração', def: 'updatedAt', width: '220px', },
      { title: 'Status', def: 'status', width: '220px', }
      // { title: 'Observação', def: 'note', width: '15%', },
    ];
  }

  override openItem(item: any, mode: OpenMode){
    // this._viewerComponent.openItem(item, mode);
  }

}
