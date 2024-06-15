import { Component } from '@angular/core';
import { PrimeIcons } from 'primeng/api';

@Component({
  selector: 'app-produto-summary',
  templateUrl: './produto-summary.component.html',
})
export class ProdutoSummaryComponent {

  events: any[] = [];

  constructor(){
    this.events = [
      { status: 'Entrada na Fila', date: '15/10/2020 10:30', icon: PrimeIcons.CHECK_CIRCLE, image: 'game-controller.jpg' },
      { status: 'Fila de Parse', date: '15/10/2020 14:00', icon: PrimeIcons.CHECK_CIRCLE, },
      { status: 'Processando', date: '15/10/2020 14:00', icon: PrimeIcons.HOURGLASS, selected: true },
      { status: 'Identificado', date: '15/10/2020 16:15', icon: PrimeIcons.CIRCLE, },
      { status: 'Enviado para Fila de Tratamento', date: '16/10/2020 10:00', icon: PrimeIcons.CIRCLE, }, 
    ];
  }
}
