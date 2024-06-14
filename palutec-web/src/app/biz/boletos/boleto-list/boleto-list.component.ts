import { Component, Injectable } from '@angular/core';
import { BoletoSummaryComponent } from '../boleto-summary/boleto-summary.component';
import { DialogService } from 'primeng/dynamicdialog';
import { BoletoCrudService } from '../boleto.service';
import { Boleto, StatusBoleto, StatusVencimentoBoleto } from '../../biz-model';
import { FileUploadComponent } from '../../../core/common/template/file-upload/file-upload.component';
import { AbstractCrudList } from '@src/app/core/service/crud/crud-impl.component';


@Component({
  selector: 'app-boleto-list',
  templateUrl: './boleto-list.component.html',
  providers: [DialogService]
})
export class BoletoListComponent extends AbstractCrudList<Boleto, BoletoSummaryComponent, BoletoCrudService>{

  columns: any[] = [];
  data: any[] = [];
  selected: any | null = null;

  myfile: any;

  constructor(service: BoletoCrudService, private upload: FileUploadComponent, viewer: BoletoSummaryComponent) {
    super(service, viewer);

    this.columns = [
      
      { title: 'Arquivo', def: 'documentoOcrFileName', width: '400px' },
      { title: 'Contrato', def: 'numeroContrato', width: '100px' },
      { title: 'Tipo de Contrato', def: 'denominacaoTipoContrato', width: '200px' },
      { title: 'Sacado', def: 'empresaContrato', width: '100px', name: 'name', align: 'center' },
      { title: 'Cedente', def: 'cedente', width: '13%', },
      // { title: 'Cedente CPF/CNPJ', def: 'cedente.cpfCnpj', width: '15%', },
      // { title: 'Data Doc.', def: 'dataDocumento', width: '15%', },
      { title: 'Valor (R$)', def: 'valor', width: '100px', name: 'name', align: 'center' },
      // { title: 'Multa (R$)', def: 'moraMulta', width: '13%', name: 'name', align: 'center' },
      { title: 'Vencimento', def: 'vencimento', width: '200px', name: 'name', align: 'center', fnElementClass:  this.statusVencimento.bind(this)},
      { title: 'Dt. Criação', def: 'createdAt', width: '200px', name: 'name', align: 'center' },
      { title: 'Dt. Modificação', def: 'updatedAt', width: '200px', name: 'name', align: 'center' },
      { title: 'Status', def: 'statusBoleto', width: '280px', name: 'name', align: 'center', fnElementClass: this.statusClass.bind(this), fnDataTransformation: this.statusLabelTransform.bind(this)},
    ];


  }
  statusClass(e: any): string{
    return StatusBoleto.fromName(e.statusBoleto)?.cssClass;
  }
  statusLabelTransform(e: any){
    return StatusBoleto.fromName(e.statusBoleto)?.description;
  }
  statusVencimento(e: any){ 
    return StatusVencimentoBoleto.fromName(e.statusVencimentoBoleto)?.cssClass;
  }

  override openToCreate(item: any, additional?: any): void {
    this.openUpload(item);
  }

  private openUpload(event: any): void {
    this.upload.open('Adicionar Boletos', '/v1/file-upload', '30%');
  }


}