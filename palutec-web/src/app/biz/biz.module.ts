import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoletoInfoComponent } from './boletos/boleto-info/boleto-info.component';
import { BoletoListComponent } from './boletos/boleto-list/boleto-list.component';
import { RouterModule, Routes } from '@angular/router';
import { CoreComponentModule } from '../core/common/template/template.module';
import { BoletoSummaryComponent } from './boletos/boleto-summary/boleto-summary.component';
import { ContratoInfoComponent } from './contrato/contrato-info/contrato-info.component';
import { ContratoListComponent } from './contrato/contrato-list/contrato-list.component';
import { DocumentoOcrListComponent } from './documento-ocr/documento-list/documento-list.component';
import { DocumentoOcrInfoComponent } from './documento-ocr/documento-info/documento-info.component';
import { DocumentoSummaryComponent } from './documento-ocr/documento-summary/documento-summary.component';
import { ContratoDespesaCrudList } from './contrato-despesa/contrato-despesa-list/contrato-despesa-list.component';
import { ContratoDespesaInfoComponent } from './contrato-despesa/contrato-despesa-info/contrato-despesa-info.component';
import { UsuarioListComponent } from './adm/usuario/usuario-list/usuario-list.component';
import { UsuarioInfoComponent } from './adm/usuario/usuario-info/usuario-info.component';
import { GrupoResolucaoInfoComponent } from './adm/grupos/grupos-info/grupos-info.component';
import { GrupoResolucaoListComponent } from './adm/grupos/grupos-list/grupos-list.component';
import { DialogService, DynamicDialogConfig } from 'primeng/dynamicdialog';


const routes: Routes = [
  { path: 'boleto-list', component: BoletoListComponent },
  { path: 'boleto-info', component: BoletoInfoComponent },
  { path: 'boleto-summary', component: BoletoSummaryComponent },
  { path: 'contrato-list', component: ContratoListComponent },
  { path: 'documento-ocr-list', component: DocumentoOcrListComponent },

  { path: 'adm/usuario-list', component: UsuarioListComponent },
  { path: 'adm/grupo-resolucao-list', component: GrupoResolucaoListComponent}

  
];

@NgModule({
  declarations: [
    BoletoInfoComponent,
    BoletoListComponent,
    BoletoSummaryComponent,
    ContratoInfoComponent,
    ContratoListComponent,
    DocumentoOcrListComponent,
    DocumentoOcrInfoComponent,
    DocumentoSummaryComponent,
    ContratoDespesaCrudList,
    ContratoListComponent,
    ContratoDespesaInfoComponent,
    UsuarioListComponent,
    UsuarioInfoComponent,
    GrupoResolucaoInfoComponent,
    GrupoResolucaoListComponent
  ],
  imports: [
    CommonModule,
    CoreComponentModule,
    RouterModule.forChild(routes),
  ],
  providers: [
    DialogService, DynamicDialogConfig
  ]
})
export class BizModule { }
