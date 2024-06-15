import { Component, Injectable, Input, OnInit } from '@angular/core';
import { ProdutoCrudService } from '../produto.service';
import { DialogService, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { ICrudViewComponent, OpenMode } from '@src/app/core/service/crud/crud.service.obj';
import { FormGroup } from '@angular/forms';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { Produto } from '../../biz-model';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-produto-info',
  templateUrl: './produto-info.component.html',
})
export class ProdutoInfoComponent implements OnInit, ICrudViewComponent{

  events: any[] = [];

  @Input() form!: FormGroup;
  @Input() id?: string;
  obj!: Produto;

  @Input() documentoId?: string;

  constructor(
      private documentoService: ProdutoCrudService, 
      private ref: DynamicDialogConfig, 
      private modal: DialogService,
      private formBuilder: RxFormBuilder, ){

    this.id = ref.data?.item?.id;
    this.obj = ref.data?.item || new Produto(); 
    this.form = this.formBuilder.formGroup(this.obj);
  }

  ngOnInit(): void {
   // this.loadLog();
  }

  openItem(item: any): void {
    this.modal.open(ProdutoInfoComponent, {
      header: 'Adicionar',
      width: '50%',
      height: '50%',
      contentStyle: { overflow: 'auto' },
      baseZIndex: 10000,
      maximizable: true,
      data: {
        item: item
      },
    });
  }
}
