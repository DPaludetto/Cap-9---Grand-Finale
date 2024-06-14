import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { IPageableList } from '@core/domain/pageable-list.interface';
import Pagination from '@interfaces/pagination';
import { ProductFilter } from '@interfaces/products';
import { ICrudService } from '@interfaces/service-crud.interface';
import { KeyValueOption } from '@shared/base/key-value.option';
import { BehaviorSubject, Observable } from 'rxjs';
import { auditTime, map, tap } from 'rxjs/operators';

export abstract class DropDownLazyComponent<S extends ICrudService<any, any>> {
  page: Pagination = {
    number: 0,
    size: 20000,
    totalElements: 0,
    totalPages: 0,
  };

  items = new BehaviorSubject<any[]>([]);

  items$!: Observable<any>;

  viewPort!: CdkVirtualScrollViewport;

  nameFilter?: string;

  constructor(protected service: S) {}

  startHandleScrollFor(viewPort: CdkVirtualScrollViewport, items$: Observable<any>): void {
    this.viewPort = viewPort ? viewPort : this.viewPort;
    this.items$ = items$;
    this.handleViewPortScroll().subscribe();
  }

  handleViewPortScroll(): Observable<number> {
    console.log(this.viewPort.scrolledIndexChange);
    return this.viewPort.scrolledIndexChange.pipe(
      auditTime(300),
      tap((firstElementIndex: number) => {
        this.nextScrollPage(firstElementIndex);
      }),
    );
  }

  nextScrollPage(firstElementIndex: number): void {
    const viewportPropSize = Math.floor(this.viewPort.getViewportSize() / this.items.getValue().length);
    if (this.viewPort.getDataLength() <= firstElementIndex + viewportPropSize) {
      this.getNextPage();
    }
  }

  getNextPage(): void {
    if (this.page.totalPages >= this.nextPageNumber) {
      this.items$ = this.createObservable(this.createFilter());
      this.items$.subscribe();
    }
  }

  get nextPageNumber(): number {
    return (this.page?.number ? this.page?.number : 0) + 1;
  }

  filtrar(event?: any): void {
    this.nameFilter = event?.filter;
    this.resetPage();
    this.startHandleScrollFor(this.viewPort, this.createObservable(this.createFilter(), true));
  }

  createObservable(filter?: ProductFilter, replace?: boolean): Observable<any> {
    this.items$ = this.service.list(filter)
      .pipe(
        tap((response) => this.updateFromPageable(response)),
        map((response: IPageableList<any>) => response.content),
        map((contents: any[]) => contents.map((content) => this.convertToOption(content))),
        tap((items) => this.updateItems(items, replace)),
      );

    return this.items$;
  }

  resetPage(): void {
    const resetPage = {
      number: 0,
      size: 20,
      totalElements: 0,
      totalPages: 0,
    };

    this.page = resetPage;
  }

  updateFromPageable(page: any): void {
    if (this.page.number <= this.nextPageNumber) {
      this.page.number += 1;
      this.page.totalElements = page.totalElements;
      this.page.totalPages = page.totalPages;
    }
  }

  updateItems(items: any, replace?: boolean): void {
    this.items.next(replace ? items : [...this.items.getValue(), ...items]);
  }

  convertToOption(value: any): any {
    return new KeyValueOption(value.id, `${value.feppId} - ${value.name}`);
  }

  abstract createFilter(): any;
}
