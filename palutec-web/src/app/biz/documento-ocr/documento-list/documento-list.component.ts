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
      { title: 'Produto', def: 'name', width: '*',  },
      { title: 'Marca', def: 'brand', width: '5%',  },
      { title: 'Tipo', def: 'type', width: '5%',  },
      { title: 'Descrição', def: 'description', width: '5%',  },
      { title: 'Preço', def: 'price', width: '100px'},
      { title: 'Qtd.', def: 'stockQuantity', width: '100px'},
      { title: 'Dt. Cadastro', def: 'createdAt', width: '100px',},
      { title: 'Dt. Alteração', def: 'updatedAt', width: '100px',},
    ];

    

    this.contextMenuItens = [
      {label: 'Abrir', icon: 'pi pi-fw pi-file-edit', command: this.openToEdit.bind(this)},
      {label: 'Remover', icon: 'pi pi-fw pi-trash', command: this.deleteDocument.bind(this)},
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
  
  

}
