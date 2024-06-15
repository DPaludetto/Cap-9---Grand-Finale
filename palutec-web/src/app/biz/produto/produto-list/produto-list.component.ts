import { Component } from '@angular/core';
import { AbstractCrudList } from '@src/app/core/service/crud/crud-impl.component';
import { Produto as Produto, StatusDocumentoOcr } from '../../biz-model';
import { DialogService } from 'primeng/dynamicdialog';
import { ProdutoInfoComponent as ProdutoInfoComponent } from '../produto-info/produto-info.component';
import { environment } from 'src/environments/environment';
import { isNgTemplate } from '@angular/compiler';
import { MenuItem } from 'primeng/api';
import { ProdutoCrudService } from '../produto.service';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  providers: [DialogService]
})
export class ProdutoListComponent extends AbstractCrudList<Produto, ProdutoInfoComponent, ProdutoCrudService>{

  columns: any[] = [];
  
  actions: any[] = [];

  contextMenuItens: MenuItem[] = [];

  constructor(private modal: DialogService, service: ProdutoCrudService, private viewer: ProdutoInfoComponent) {
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
