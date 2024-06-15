import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CoreComponentModule } from '../core/common/template/template.module';

import { UsuarioListComponent } from './adm/usuario/usuario-list/usuario-list.component';
import { UsuarioInfoComponent } from './adm/usuario/usuario-info/usuario-info.component';
import { DialogService, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { ProdutoSummaryComponent } from './produto/documento-summary/produto-summary.component';
import { ProdutoInfoComponent } from './produto/produto-info/produto-info.component';
import { ProdutoListComponent } from './produto/produto-list/produto-list.component';


const routes: Routes = [
  { path: 'cadastro-list', component: ProdutoListComponent },

  { path: 'adm/usuario-list', component: UsuarioListComponent },

  
];

@NgModule({
  declarations: [
    ProdutoListComponent,
    ProdutoInfoComponent,
    ProdutoSummaryComponent,
    UsuarioListComponent,
    UsuarioInfoComponent,
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
