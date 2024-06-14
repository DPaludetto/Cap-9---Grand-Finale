import { Component, Injectable, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { Despesa } from '../../biz-model';
import { ICrudViewComponent, OpenMode } from '@src/app/core/service/crud/crud.service.obj';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-contrato-despesa-info',
  templateUrl: './contrato-despesa-info.component.html',
})
export class ContratoDespesaInfoComponent implements OnInit, ICrudViewComponent{

  @Input() form!: FormGroup;

  // responsaveis: any[];
  responsaveisSelected: any[] = [];
  gruposResolucao: any[];

  @Input() contratoDespesa: any;

  @Input() idContrato?: string;

  constructor(private formBuilder: RxFormBuilder){

    this.gruposResolucao = [
      { label: 'Grupo de resolução de despesas de aluguéis.', value: { id: '1', name: 'Grupo de resolução de despesas de aluguéis.', code: 'ALUGUEL' } },
      { label: 'Grupo de resolução de despesas de condomínio.', value: { id: '2', name: 'Grupo de resolução de despesas de condomínio.', code: 'CONDOMINIO' } },
  ];

    this.contratoDespesa = new Despesa();
    this.form = this.formBuilder.formGroup(this.contratoDespesa);
    // this.form = this.formBuilder.formGroup(Despesa);
  }

  openItem(item: any, mode: OpenMode): void {
    throw new Error('Method not implemented.');
  }
  ngOnInit(): void {
    console.log(this.idContrato);
  }

  test(){
    console.log(this.responsaveisSelected);
  }

  
}
