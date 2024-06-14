import { Component, Injectable, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ContratoCrudService } from '../contrato.service';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { Contrato } from '../../biz-model';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ContratoDespesaCrudService } from '../../contrato-despesa/contrato-despesa-list/contrato-despesa-list.service';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-contrato-info',
  templateUrl: './contrato-info.component.html',
  providers: [
    DialogService
  ]
})
export class ContratoInfoComponent implements OnInit{

  @Input() form!: FormGroup;
  @Input() id?: string;
  
  obj!: Contrato;

  responsavelColumns: any = [];



  boletosColumns: any[] = [];
  despesasColumns: any[] = [];
  despesasDatasource: any[] = [];

  despesa: any;

  dialogRef?: DynamicDialogRef;

  constructor(
      private service: ContratoCrudService, 
      private formBuilder: RxFormBuilder, 
      private ref: DynamicDialogConfig, 
      private modal: DialogService,
      
      private contratoDespesaService: ContratoDespesaCrudService){
  
    this.id = ref.data?.item?.id;
    this.obj = new Contrato(); 
    this.form = this.formBuilder.formGroup(this.obj);




    this.boletosColumns = [
      { title: 'Arquivo', def: 'documentoOcrFileName', width: '400px',},
      { title: 'Data Doc.', def: 'dataDocumento', width: '15%', },
      { title: 'Valor (R$)', def: 'valor', width: '13%', name: 'name', align: 'center' },
      { title: 'Multa (R$)', def: 'moraMulta', width: '13%', name: 'name', align: 'center' },
      { title: 'Vencimento', def: 'vencimento', width: '13%', name: 'name', align: 'center' },
      { title: 'Despesa', def: 'statusBoleto', width: '13%', name: 'name', align: 'center' },
      { title: 'Responsável', def: 'statusBoleto', width: '13%', name: 'name', align: 'center' },
      { title: 'Dt. Criação', def: 'createdAt', width: '13%', name: 'name', align: 'center' },
      { title: 'Dt. Modificação', def: 'updatedAt', width: '13%', name: 'name', align: 'center' },
      { title: 'Status', def: 'statusBoleto', width: '13%', name: 'name', align: 'center' },
    ];

    

    this.despesasColumns = [
      // { title: 'Tipo Movimento', def: 'documentoOcrFileName', width: '400px', },
      { title: 'Tipo Despesa', def: 'denominacaoTipoContrato', width: '8%',},
      { title: 'Descrição Fluxo', def: 'descricaoFluxo', width: '*', },
      { title: 'Cód. Fornecedor SAP', def: 'codigoFornecedor', width: '8%', name: 'name', align: 'center' },
      { title: 'Contrato Antigo', def: 'contratoAntigo', width: '8%', name: 'name', align: 'center' },
      { title: 'Centro Custo SAP', def: 'centroCusto', width: '8%', name: 'name', align: 'center' },
      { title: 'Criação do Registro', def: 'createdAt', width: '8%', name: 'name', align: 'center' },
      { title: 'Última Atualização', def: 'updatedAt', width: '8%', name: 'name', align: 'center' },

    ]


  }

  ngOnInit(): void {
    
    this.service.getById(this.id).subscribe((e)=>{
      this.obj = new Contrato();
      Object.assign(this.obj, e);
      this.form = this.formBuilder.formGroup(this.obj);
    });

    this.service.getContratoDespesaByContratoId(this.id).subscribe(e =>{
      this.despesasDatasource = e;
    });

  }

  onDespesaRowSelected(e: any){
    this.despesa = e;
  }
  onBoletoRowSelected(e: any){
    this.obj = e;
  }

  openItem(item: any): void {
    this.dialogRef = this.modal.open(ContratoInfoComponent, {
      header: `Contrato ${item.numeroContrato} - ${item.denominacaoTipoContrato}`,
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

  cancel(): void{
    this.dialogRef?.close();
  }

  save(): void{
    this.dialogRef?.close();
  }


}
