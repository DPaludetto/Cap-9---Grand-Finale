import {
  Component, Input, Output, EventEmitter, ViewChild, OnInit, ElementRef,
} from '@angular/core';


import { LazyLoadEvent, MenuItem, SortEvent } from 'primeng/api';
import { Table } from 'primeng/table';
import { Action } from './action.interface';
import { ColumnData } from './column-data.interface'; 
import { FormGroup } from '@angular/forms';
import { IPaginatorService } from '@src/app/core/service/crud/crud.service.obj';
import Page, { PaginationHelper } from '@src/app/core/service/crud/pagination';
import { Subject } from 'rxjs/internal/Subject';
import { timer, tap, takeUntil } from 'rxjs';
import { DateUtils } from '@src/app/core/utils/date-utils';

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
})
export class DataTableComponent implements OnInit{

  @ViewChild('table') dataTable!: Table;

  @Input() form = new FormGroup({});

  @Input() columns: ColumnData[] = [];

  @Input() dataSource: any[] = [];

  @Input() dataKey = 'id';

  @Input() displayedColumns: string[] = [];



  @Input() showPagination = true;

  

  @Input() actions: Action[] = [];

  @Input() showEditColumn: boolean;

  

  @Output() columnFilterEvent = new EventEmitter();

  @Input() showRemoveColumn: boolean;

  @Output() editRow = new EventEmitter();

  @Output() removeRow = new EventEmitter();

  @Output() onRowSelect = new EventEmitter();
  @Output() onSelectAll = new EventEmitter();
  
  @Output() selectedRowsArray = new EventEmitter();

  @Output() pageEvent = new EventEmitter<Page<any>>();

  @Input() multipleSelection = false;
  

  selectedItens: any[] = [];

  pageNavigation = [10, 20, 50, 100];
  page: Page<any>;
  @Input() paginatorService?: IPaginatorService<any>;

  @Input() contextMenuItens: any[] = [];

  @Output() selectedContextMenuItem?: any;

  @Input() refreshIntervalInSeconds = 10;

  private unsub = new Subject<void>();


  constructor() {
    this.page = PaginationHelper.createDefaultPaginator();
    this.showEditColumn = true;
    this.showRemoveColumn = true;

  }
  
  ngOnInit(): void {

    timer(0, this.refreshIntervalInSeconds * 1000)
    .pipe(
      tap(() => this.loadData()),
      takeUntil(this.unsub)
    )
    .subscribe();
    
    this.loadData();
  }

  get activatePaginator(): boolean{
    return this.paginatorService != undefined && this.page.totalElements > PaginationHelper.DEFAULT_PAGE_SIZE;
  }

  loadData() {
    this.paginatorService?.listPage(this.page)
      .subscribe((e :any) => {
        this.page.totalElements = e.totalElements;
        this.page.totalPages =  e.totalPages;
        this.dataSource = e.content;
      });

  }


  searchText(event: any): void {
    if (event.keyCode === 13) {
      this.executeFilter();
    }
  }

  searchCombo(event: any, columnName: string): void {
    const selectTag = document.getElementById(columnName) as HTMLSelectElement;
    selectTag.value = event.value;
    this.executeFilter();
  }

  executeFilter(extFilter?: any): void {
    let filter: any = { } ;
    this.columns.forEach((coluna: any) => {
      if (coluna.filter === 'dropdown') {
        const selectTag = document.getElementById(coluna.def) as HTMLSelectElement;
        filter[coluna.name] = selectTag.value;
      } else if (coluna.filter === 'text' || coluna.filter === 'date') {
        const inputTag = document.getElementById(coluna.def) as HTMLInputElement;
        filter[coluna.def] = inputTag?.value;
      }
    });

    if(extFilter){
      filter = Object.assign(filter, extFilter);
    }
    
    this.page.filter = filter;
    this.resetPagination();
    this.loadData();
    this.columnFilterEvent.emit(filter);
  }

  searchDate(event: any, columnName: string): void {
    const inputTag = document.getElementById(columnName) as HTMLInputElement;
    if (event instanceof Date) {
      inputTag.value = DateUtils.dateToStringFormatPresentation(event);
    } else {
      inputTag.value = '';
    }
    const filter: any = {};
    filter[columnName] = inputTag.value;
    this.executeFilter(filter);
  }

  resetPagination(): void {
    this.dataTable.first = 0;
    this.page.pageNumber = 0;
  }

  _onRowSelect(element: any): void {
    this.onRowSelect.emit(element);
    this.selectedRowsArray.emit(this.selectedItens);
  }

  _onSelectAll(element: any){
    this.onSelectAll.emit(this.selectedItens);
    this.selectedRowsArray.emit(this.selectedItens);
  }

  pagination(event: any): void {
     this.page = {
      pageNumber: (event.first || 0) / (event.rows || 1),
      pageSize: event.rows,
    } as Page<any>,
   // this.page.sort = this.columns[0].def
    
    this.loadData();
    
    this.pageEvent.emit(this.page);
  }

  sort(event: SortEvent) {
    console.log(event);
  }

  getIcon(name: string): string {
    return `mask: url('/assets/imgs/${name}.svg') no-repeat !important`;
  }

  hasData(): boolean {
    return this.dataSource.length > 0;
  }



  //Attaching selected row item in menu context model using element variable
  onContextMenu(event: any){
    this.attachItem(this.contextMenuItens);
  }
  //Recursive attachment...
  attachItem(items: any[]){
    items.forEach(e =>{
      e.element = this.selectedContextMenuItem;
      if(e.items){
        this.attachItem(e.items)
      }
    });
  }

  ngOnDestroy(): void {
    this.unsub.next();
    this.unsub.complete();
  }

  exportData() {
    this.paginatorService?.exportData(this.page);
  }


}
