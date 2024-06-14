import { Component, Injectable, Input, OnInit } from '@angular/core';
import { PrimeIcons } from 'primeng/api';
import { StatusDocumentoOcr } from '../../biz-model';
import { DocumentoOcrCrudService } from '../documento-ocr.service';
import { DialogService, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { ICrudViewComponent, OpenMode } from '@src/app/core/service/crud/crud.service.obj';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-documento-info',
  templateUrl: './documento-info.component.html',
})
export class DocumentoOcrInfoComponent implements OnInit, ICrudViewComponent{

  events: any[] = [];

  logColumns: any[] = [];
  logSource: any[] = [];

  @Input() documentoId?: string;

  constructor(private documentoService: DocumentoOcrCrudService, private ref: DynamicDialogConfig, private modal: DialogService){

    this.logColumns = [
      { title: 'Data', def: 'updatedAt', width: '15%', },
      { title: 'Status', def: 'status', width: '30%',fnElementClass: this.statusClass.bind(this), fnDataTransformation: this.statusLabelTransform.bind(this)},
      { title: 'Observação', def: 'note', width: '*', },
    ];
  }

  ngOnInit(): void {
    this.loadLog();
  }

  statusClass(e: any): string{
    return StatusDocumentoOcr.fromName(e.status)?.cssClass;
  }
  statusLabelTransform(e: any){
    return StatusDocumentoOcr.fromName(e.status)?.description;
  }

  loadLog(){
    this.documentoId = this.ref.data.item.id;
    this.documentoService.loadProcessLog(this.documentoId).subscribe(e =>{
      this.logSource = e;
    });
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
