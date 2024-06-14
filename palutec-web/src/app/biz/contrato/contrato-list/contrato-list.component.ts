import { Component } from '@angular/core';
import { Contrato } from '../../biz-model';
import { ICrudComponent, ICrudService } from '@src/app/core/service/crud/crud.service.obj';
import { AbstractCrudList } from '@src/app/core/service/crud/crud-impl.component';
import { DialogService } from 'primeng/dynamicdialog';
import { ContratoCrudService } from '../contrato.service';
import { ContratoInfoComponent } from '../contrato-info/contrato-info.component';

@Component({
  selector: 'app-contrato-list',
  templateUrl: './contrato-list.component.html',
  providers: [DialogService]
})
export class ContratoListComponent extends AbstractCrudList<Contrato, ContratoInfoComponent, ContratoCrudService>{

  columns: any[];

  constructor(private modal: DialogService, service: ContratoCrudService, viewer: ContratoInfoComponent) {
    super(service, viewer);

    this.columns = [
      { title: 'Empresa', def: 'empresa', width: '100px',  },
      { title: 'Número Contrato', def: 'numeroContrato', width: '100px', },
      { title: 'Tipo de Contrato', def: 'denominacaoTipoContrato', width: '100px',  },
      { title: 'Responsável', def: 'responsavel', width: '100px',  },
      { title: 'Criação', def: 'createdAt', width: '100px', },
      { title: 'Ult. Altaração', def: 'updatedAt', width: '100px',},
    ];

  }

}
