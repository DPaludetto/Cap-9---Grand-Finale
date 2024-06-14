import {
  Component, ContentChild, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild,
} from '@angular/core';
import { Router } from '@angular/router';
import { map, switchMap, takeUntil, tap } from 'rxjs/operators';
import { LazyLoadEvent, MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { ICrudComponent, ICrudService, IPaginatorService } from '@src/app/core/service/crud/crud.service.obj';
import { FormGroup } from '@angular/forms';
import { JsonUtils } from '@src/app/core/utils/json.utils';
import { Action } from '../data-table/action.interface';
import Page, { PaginationHelper } from '@src/app/core/service/crud/pagination';
import { DialogService } from 'primeng/dynamicdialog';
import { DataTableComponent } from '../data-table/data-table.component';

@Component({
  selector: 'app-list-page-template',
  templateUrl: './list-page-template.component.html',
  styleUrls: ['./list-page-template.component.scss'],
})
export class ListPageTemplateComponent{

  @ViewChild('dataTable') dataTable!: DataTableComponent;

  @Input() form = new FormGroup({});

  @Input() pageTitle = '';

  @Input() paginator!: IPaginatorService<any>;

  @Input() columns: any[] = [];

  @Input() displayedColumns: string[] = [];

  @Input() dataSource: any[] = [];

  @Input() idName!: string;

  @Input() deleteMessage = '';

  @Input() showPagination = true;

  @Input() createButtonName = 'Adicionar';

  @Input() showCreateButton: boolean;

  @Input() searchSingle: boolean;

  @Input() aditionalRouteParam: string[];

  @Input() filterFields: string[];

  @Input() titleStyle?: any;
  
  @Output() filterEvent = new EventEmitter();

  @Output() columnFilterEvent = new EventEmitter();

  @Output() pageEvent = new EventEmitter<Page<any>>();

  @Output() deleteEvent = new EventEmitter();

  @Output() selectItemEvent = new EventEmitter();

  @Output() selectRow = new EventEmitter();

  @Input() selectedItens: any[] = [];

  @Input() crudComponent!: ICrudComponent<any>;

  @Output() onRowSelect = new EventEmitter();
  @Output() onSelectAll = new EventEmitter();

  @Input() actions: Action[] = [];
  @Input() contextMenuItens: MenuItem[] = [];

  @Input() multipleSelection = false;

  @Input() exportButtonName = 'Exportar';
  @Input() showExportData = true;

  parentId: any;

  constructor(
    private router: Router,
    // private modalService: NgbModal,
  ) {
    this.paginator ? this.paginator : PaginationHelper.createDefaultPaginator();
    this.showCreateButton = true;
    this.searchSingle = true;
    this.aditionalRouteParam = [];
    this.filterFields = [];
  }

  
  get paginationService(): IPaginatorService<any>{
    return this.crudComponent?.service;
  }

  navigateToCreate(): void {
    this.crudComponent?.openToCreate(this.parentId, this.aditionalRouteParam);
    // this.router.navigate([`${this.crudUrl}/create`, { mode: 'CREATE', ...this.aditionalRouteParam }]);
  }

  navigateToDetail(item: any): void {
    this.selectItemEvent.emit(item);
    if(!this.multipleSelection){
      this.crudComponent?.openToView(item);
    }

  }

  search(value: string): void {
    // this.resetPagination();
    // this.filterEvent.emit(value);
  }

  searchText(event: any): void {
    // if (event.keyCode === 13) {
    //   this.executeFilter();
    // }
  }

  searchDate(event: any, columnName: string): void {
    // const inputTag = document.getElementById(columnName) as HTMLInputElement;
    // if (event instanceof Date) {
    //   inputTag.value = event.toString();
    // } else {
    //   inputTag.value = '';
    // }
    // this.executeFilter();
  }

  searchCombo(event: any, columnName: string): void {
    // const selectTag = document.getElementById(columnName) as HTMLSelectElement;
    // selectTag.value = event.value;
    // this.executeFilter();
  }

  executeFilter(): void {
    // const filter: any = { };
    // this.columns.forEach((coluna: any) => {
    //   if (coluna.filter === 'dropdown') {
    //     const selectTag = document.getElementById(coluna.columnFilter) as HTMLSelectElement;
    //     filter[coluna.columnFilter] = selectTag.value;
    //   } else if (coluna.filter === 'text' || coluna.filter === 'date') {
    //     const inputTag = document.getElementById(coluna.columnFilter) as HTMLInputElement;
    //     filter[coluna.columnFilter] = inputTag.value;
    //   }
    // });
    // this.resetPagination();
    // filter.pagination = this.paginator;
    // this.columnFilterEvent.emit(filter);
  }

  resetPagination(): void {
    //this.dataTable.first = 0;
    //this.paginator.number = 0;
  }

  open(content: any, item: any): void {
    // this.selectItemEvent.emit(item);
    // this.modalService.open(content).result.then((result) => {
    //   if (result === 'accept') {
    //     this.remove(item);
    //   }
    // }, () => {
    //   this.selectItemEvent.emit(null);
    // });
  }

  edit(item: any): void {
     this.crudComponent.openToEdit(item);
    // this.router.navigate([`${this.crudUrl}/detail/${item[this.idName]}`, { mode: 'EDIT' }]);
  }

  remove(item: any): void {
    this.crudComponent.service.delete(item);
    this.deleteEvent.emit(item);
  }

  pagination(event: LazyLoadEvent): void {
    // this.pageEvent.emit(
    //   {
    //     number: (event.first || 0) / (event.rows || 1),
    //     size: event.rows,
    //     totalElements: this.totalElements,
    //     totalPages: this.totalElements / (event.rows || 1),
    //   } as Pagination,
    // );
  }

  loadData(): void{
    this.dataTable.loadData();
  }

  exportData(): void{
    this.dataTable.exportData();
  }

  getElementValue(element: any, column: any): string{
    let val = JsonUtils.findValue(element, column.def);
    val = column.transform !== undefined ? column.transform(element) : val;
    
    return val;
  }
  getElementClass(element: any, column: any): string {
    // console.log(`${element}: ${column.title}`)
    return column.itemClass ? column.itemClass(element) : '';
  }
  



}
