import { Component, Injectable, Output } from '@angular/core';
import { PrimeIcons } from 'primeng/api';
import { DialogService, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { environment } from 'src/environments/environment';
import { Boleto } from '../../biz-model';
import { ICrudViewComponent, OpenMode } from '@src/app/core/service/crud/crud.service.obj';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-boleto-summary',
  templateUrl: './boleto-summary.component.html',
  providers: [
    DialogService
  ]
})
export class BoletoSummaryComponent implements ICrudViewComponent{

  events: any[] = [];
  urlImage = '';

  @Output()
  boleto?: Boleto;

  constructor(
    private ref: DynamicDialogConfig,
    private modal: DialogService
  ){
    
    this.urlImage = `${environment.apiUrl}/boleto/${ref.data?.item.id}/imagem-boleto`;
  }

  ngOnInit() {
    this.boleto = this.ref.data?.item;

    // this.events = [
    //     { status: 'Ordered', date: '15/10/2020 10:30', icon: PrimeIcons.SHOPPING_CART, color: '#9C27B0', image: 'game-controller.jpg' },
    //     { status: 'Test', date: '15/10/2020 14:00', icon: PrimeIcons.CHART_PIE, color: '#673AB7', selected: true },
    //     { status: 'Processing', date: '15/10/2020 14:00', icon: PrimeIcons.COG, color: '#673AB7' },
    //     { status: 'Shipped', date: '15/10/2020 16:15', icon: PrimeIcons.ENVELOPE, color: '#FF9800' },
    //     { status: 'Delivered', date: '16/10/2020 10:00', icon: PrimeIcons.CHECK, color: '#607D8B' }
    // ];

    this.events = [
      { status: 'Fila de Email', date: '15/10/2020 10:30', icon: PrimeIcons.CHECK_CIRCLE, image: 'game-controller.jpg' },
      { status: 'Fila de Parse', date: '15/10/2020 14:00', icon: PrimeIcons.CHECK_CIRCLE, },
      { status: 'Processando', date: '15/10/2020 14:00', icon: PrimeIcons.HOURGLASS, selected: true },
      { status: 'Identificado', date: '15/10/2020 16:15', icon: PrimeIcons.CIRCLE, },
      { status: 'Enviado para Fila de Pagamento', date: '16/10/2020 10:00', icon: PrimeIcons.CIRCLE, },
      { status: 'Boleto Pago', date: '16/10/2020 10:00', icon: PrimeIcons.CIRCLE, }
    ];
    // this.events = ['21', '2'];


  }

   openItem(item: any, mode: OpenMode): void {
    this.modal.open(BoletoSummaryComponent, {
      header: 'Informações do Boleto',
      width: '80%',
      height: '98%',
      contentStyle: { overflow: 'auto' },
      baseZIndex: 10000,
      maximizable: true,
      data: {
        item: item
      },
    });
  }

}



