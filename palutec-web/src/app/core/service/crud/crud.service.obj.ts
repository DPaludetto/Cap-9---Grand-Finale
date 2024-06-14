import { Observable } from 'rxjs';
import Page from './pagination';


export enum OpenMode {
  NEW, VIEW, EDIT
}

export interface ICrudComponent<T> {
  
  service: ICrudService<T>;
  
  openToEdit(item: any): void;
  openToCreate(item: any, additional?: any, ): void;
  openToView(item: any): void;

}

export declare interface IPaginatorService<T>{
  listPage(page?: Page<T>, filter?: any | undefined): Observable<any>;

  exportData(page?: Page<T>, filter?: any | undefined): void;
}

export declare interface ICrudService<T> extends IPaginatorService<T>{

  list(filter?: any): Observable<any>;

  getById(id: any): Observable<T>;

  create(type: T): Observable<T>;

  update(type: T | T[]): Observable<T>;

  delete(id: any): Observable<any>;

}



export declare interface ICrudViewComponent {
  openItem(item: any, mode: OpenMode): void;
}


