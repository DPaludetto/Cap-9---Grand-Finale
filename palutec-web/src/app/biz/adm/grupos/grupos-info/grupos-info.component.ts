import { Component, Injectable, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { GrupoResolucao } from '@src/app/biz/biz-model';
import { ICrudViewComponent, OpenMode } from '@src/app/core/service/crud/crud.service.obj';
import { DialogService, DynamicDialogConfig, } from 'primeng/dynamicdialog';


@Injectable({providedIn: 'root'})
@Component({
  selector: 'app-grupo-resolucao-info',
  templateUrl: './grupo-resolucao-info.component.html',
  styleUrls: ['./grupo-resolucao-info.component.scss'],
  providers: [
    DialogService
  ]  
})
export class GrupoResolucaoInfoComponent implements OnInit, ICrudViewComponent{

  title = 'Grupo de Resolução';

  obj!: GrupoResolucao;

  form!: FormGroup;

  constructor(private ref: DynamicDialogConfig, private dialogService: DialogService){
    
  }

  ngOnInit(): void {
    // throw new Error('Method not implemented.');
  }

  openItem(item: any, mode: OpenMode){

    this.dialogService.open(GrupoResolucaoInfoComponent, {
      header: `Grupo de Resolucao ${item?.nome}`,
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

  
}
