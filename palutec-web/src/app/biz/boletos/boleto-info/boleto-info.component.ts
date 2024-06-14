import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { environment } from '@src/environments/environment';
import { Boleto, ContratoDespesa, Despesa } from '../../biz-model';
import { FormGroup } from '@angular/forms';
import { BoletoCrudService } from '../boleto.service';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { ContratoDespesaCrudService } from '../../contrato-despesa/contrato-despesa-list/contrato-despesa-list.service';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AutoComplete } from 'primeng/autocomplete';

@Component({
  selector: 'app-boleto-info',
  templateUrl: './boleto-info.component.html'
})
export class BoletoInfoComponent implements OnInit{

  @Input() 
  title = '';

  @Input()
  id?: string;
  
  displayModal = false;
  
  urlImage?: string;

  @Input() form!: FormGroup;
  
  obj?: Boleto;

  filteredContratoDespesa: any[] = [];

  selectedContratoDespesa: any;


  constructor(private boletoService: BoletoCrudService, private formBuilder: RxFormBuilder, private contratoDespesa: ContratoDespesaCrudService, private ref: DynamicDialogRef){
    this.obj = new Boleto();
    this.form = this.formBuilder.formGroup(this.obj);
  }
  
  ngOnInit(): void {
    
    this.urlImage = `${environment.apiUrl}/boleto/${this.id}/imagem-boleto`;

    this.boletoService.getById(this.id).subscribe((e)=>{
      this.obj = new Boleto();
      Object.assign(this.obj, e);
      this.form = this.formBuilder.formGroup(this.obj);
      this.filterContratoDespesa(this.obj.contratoDespesa?.id);
      this.selectedContratoDespesa = {label: `${this.obj.contratoDespesa?.codigoFornecedor} - ${this.obj.contratoDespesa?.descricaoFluxo}`, value: this.obj.contratoDespesa?.id};
    });
  }

  close(){
    this.ref.close();
  }

  save(){
    this.close();
  }

  doAction(): void{
    alert('');
  }

  filterContratoDespesa(query: any){
    if(query.length > 0){
      this.contratoDespesa.searchByText(query, ['codigoFornecedor', 'descricaoFluxo', 'enderecoImovel', 'id'])
        .subscribe(result =>{
          this.filteredContratoDespesa = result.content.map((e: any) => {return {label: `${e.codigoFornecedor} - ${e.tipoDespesa} - ${e.descricaoFluxo}`, value: e}} );
      });
    }
  }

  download(){
    this.boletoService.downloadDocument(this.obj?.documentoOcrFileId);
  }
}
