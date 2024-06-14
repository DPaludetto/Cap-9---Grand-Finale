import { IResponsePageableList } from '@core/models/pageable-list.interface';
import { ToastService } from '@core/services/toast.service';
import { Domain } from '@interfaces/domain';
import Pagination, { PaginationHelper } from '@interfaces/pagination';
import { ICrudService } from '@interfaces/service-crud.interface';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';

/**
 * <T> Domain type
 * <ID> ID type of domain
 * <F> Filter type
 * <S> Service type
 */
export abstract class ListCrudBaseComponent<T extends Domain<ID>, ID, F, S extends ICrudService<any, ID>> {
  columns: any[] = [];

  data: T[] = [];

  paginator: Pagination;

  selected: T | null = null;

  filter: any;

  constructor(protected service: S, private toastService: ToastService) {
    this.paginator = PaginationHelper.createDefaultPaginator();
  }

  delete(t: T): void {
    this.service.delete(t.id).subscribe(() => {
      this.loadData(this.paginator);
      this.toastService.showSuccess(this.getSuccessMessage(t));
    },
    () => {
      this.toastService.showDanger(this.getErrorMessage(t));
    });
  }

  filterColumnData(eventFilter: any): void {
    this.filter = eventFilter;
    var pagination = eventFilter.pagination;
    if(!pagination){
      pagination =  PaginationHelper.createDefaultPaginator();
    }
    this.executeListData(pagination);
  }

  loadData(paginator: Pagination): void {
    // PRECISA MORRER, só esta servindo para zerar o filtro
    this.filter = { };
    this.executeListData(paginator);
  }

  executeListData(paginator: Pagination): void {
    this.createObservable(paginator).subscribe();
  }

  createObservable(paginator: Pagination): Observable<any> {
    return this.service.list({ page: paginator, ...this.filter, ...this.getFilterParameters() })
      .pipe(
        tap((response: IResponsePageableList) => this.createPaginator(response)),
        map((response: IResponsePageableList) => response.content.map((item) => this.convertData(item))),
        tap((response: any) => this.load(response)),
      );
  }

  createPaginator(values: IResponsePageableList): void {
    this.paginator.number = values.number;
    this.paginator.size = values.size;
    this.paginator.totalElements = values.totalElements;
    this.paginator.totalPages = values.totalPages;
  }

  load(values: any[]): void {
    this.data = values;
  }

  onPageChange(paginator: Pagination): void {
    this.executeListData(paginator);
  }

  // Caso precise fazer uma conversão especifica
  convertData(from: any): any {
    return from;
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  getSuccessMessage(input: T): string {
    return 'Sucesso ao excluir registro.';
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  getErrorMessage(input: T): string {
    return 'Erro ao excluir registro.';
  }

  getFilterParameters(): any {
    return { };
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  loadSearchData(value: F): void {

  }
}
