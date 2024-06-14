import { Component, Injectable, Input, OnInit } from '@angular/core';
import { DocumentoOcrCrudService } from '../documento-ocr.service';
import { DialogService, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { ICrudViewComponent, OpenMode } from '@src/app/core/service/crud/crud.service.obj';
import { FormGroup } from '@angular/forms';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { Product } from '../../biz-model';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-documento-info',
  templateUrl: './documento-info.component.html',
})
export class DocumentoOcrInfoComponent implements OnInit, ICrudViewComponent{

  events: any[] = [];

  @Input() form!: FormGroup;
  @Input() id?: string;
  obj!: Product;

  @Input() documentoId?: string;

  constructor(
      private documentoService: DocumentoOcrCrudService, 
      private ref: DynamicDialogConfig, 
      private modal: DialogService,
      private formBuilder: RxFormBuilder, ){

    this.id = ref.data?.item?.id;
    this.obj = ref.data?.item || new Product(); 
    this.form = this.formBuilder.formGroup(this.obj);
  }

  ngOnInit(): void {
   // this.loadLog();
  }

  openItem(item: any): void {
    this.modal.open(DocumentoOcrInfoComponent, {
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
