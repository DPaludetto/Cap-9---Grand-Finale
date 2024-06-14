import { Component } from '@angular/core';
import { AbstractCrudList } from '@src/app/core/service/crud/crud-impl.component';
import { DocumentoOcr, StatusDocumentoOcr } from '../../biz-model';
import { DialogService } from 'primeng/dynamicdialog';
import { DocumentoOcrCrudService } from '../documento-ocr.service';
import { DocumentoOcrInfoComponent } from '../documento-info/documento-info.component';
import { FileUploadComponent } from '@src/app/core/common/template/file-upload/file-upload.component';
import { environment } from 'src/environments/environment';
import { isNgTemplate } from '@angular/compiler';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-documento-list',
  templateUrl: './documento-list.component.html',
  providers: [DialogService]
})
export class DocumentoOcrListComponent extends AbstractCrudList<DocumentoOcr, DocumentoOcrInfoComponent, DocumentoOcrCrudService>{

  columns: any[] = [];
  
  actions: any[] = [];

  contextMenuItens: MenuItem[] = [];

  constructor(private modal: DialogService, service: DocumentoOcrCrudService, private upload: FileUploadComponent, private viewer: DocumentoOcrInfoComponent) {
    super(service, viewer);

    this.columns = [
      { title: 'Nome do Arquivo', def: 'fileName', width: '*',  },
      { title: 'Tipo', def: 'tipoDocumento', width: '5%',  },
      { title: 'Dt. Upload', def: 'createdAt', width: '15%',},
      { title: 'Dt. Ultimo Processamento', def: 'updatedAt', width: '15%',},
      { title: 'Tamanho', def: 'fileSizeReadble', width: '100px'},
      { title: 'Status', def: 'status', width: '200px', fnElementClass: this.statusClass.bind(this), fnDataTransformation: this.statusLabelTransform.bind(this)},
      { title: 'Observação', def: 'note', width: '25%', },
    ];



    this.contextMenuItens = [
      {label: 'Abrir', icon: 'pi pi-fw pi-file-edit', command: this.openToEdit.bind(this)},
      {label: 'Remover', icon: 'pi pi-fw pi-trash', command: this.deleteDocument.bind(this)},
      {separator: true},
      {label: 'Reprocessar', icon: 'pi pi-fw pi-refresh', command: this.reprocessDocument.bind(this)},
      {label: 'Download', icon: 'pi pi-fw pi-download', command: this.downloadDocument.bind(this)},
      {label: 'Rotear', icon: 'pi pi-fw pi-arrow-down-right', items: [
          {label: 'Fila de Boletos', icon: 'pi pi-fw pi-bars', command: this.forwardBoleto.bind(this)}
        ]
      },
    ];
  }

  statusClass(e: any): string{
    return StatusDocumentoOcr.fromName(e.status)?.cssClass;
  }
  statusLabelTransform(e: any){
    return StatusDocumentoOcr.fromName(e.status)?.description;
  }
  forwardBoleto(e: any){
    this.service.setDocumentType(e.item.element.id, 'BOLETO');
  }
  reprocessDocument(e: any){
    this.service.reprocessDocument(e.item.element.id);
  }

  downloadDocument(e: any){
    this.service.downloadDocument(e.item.element);
  }

  deleteDocument(e: any){
    this.service.delete(e.item.element.id).subscribe();
  }
  
  
  override openToCreate(item: any, additional?: any): void {
    this.openUpload(item);
  }
  

  private openUpload(event: any): void {
    this.upload.open('Adicionar Documento', `${environment.apiUrl}/v1/file-upload/OCR`);
  }


}
