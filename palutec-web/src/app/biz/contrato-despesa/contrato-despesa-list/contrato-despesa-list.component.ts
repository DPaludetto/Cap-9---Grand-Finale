import { Component, Input } from '@angular/core';
import { Despesa } from '../../biz-model';
import { AbstractCrudList } from '@src/app/core/service/crud/crud-impl.component';
import { DialogService } from 'primeng/dynamicdialog';
import { ContratoDespesaCrudService } from './contrato-despesa-list.service';
import { ContratoDespesaInfoComponent } from '../contrato-despesa-info/contrato-despesa-info.component';
import { OpenMode } from '@src/app/core/service/crud/crud.service.obj';

@Component({
  selector: 'app-contrato-despesa-list',
  templateUrl: './contrato-despesa-list.component.html',
})
export class ContratoDespesaCrudList extends AbstractCrudList<Despesa, ContratoDespesaInfoComponent, ContratoDespesaCrudService>{


  @Input() title = '';
  
  columns: any = [];
  dataSource: any[] = [];
  
  constructor(private modal: DialogService, service: ContratoDespesaCrudService, viewer: ContratoDespesaInfoComponent) {
    super(service, viewer);

    this.columns = [
      
      { title: 'Contrato', def: 'codigoContrato', width: '100px' },
      { title: 'Fornecedor', def: 'codigoFornecedor', width: '100px'},
      { title: 'Descrição Fluxo', def: 'descricaoFluxo', width: '13%'},
      { title: 'Imóvel', def: 'enderecoImovel', width: '*', },
      { title: 'Tipo do Movimento', def: 'tipoMovimento', width: '15%',},
      { title: 'Tipo da Despesa', def: 'tipoDespesa', width: '15%', },
      { title: 'Descrição', def: 'descricao', width: '13%'},
      { title: 'Tipo do Movimento SAP', def: 'tipoMovimentoSap', width: '13%'},
      { title: 'Centro de Custo SAP', def: 'tipoCondicaoSap', width: '13%'},
      { title: 'Dt. Criação', def: 'createdAt', width: '13%'},
      { title: 'Dt. Modificação', def: 'updatedAt', width: '13%'},
    ];
  }

}
