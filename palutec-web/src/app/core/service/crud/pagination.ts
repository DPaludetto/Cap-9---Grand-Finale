
export default interface Page<T> {
  elements?: T[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  filter?: any;
  sort?: string;
}


export class PaginationHelper {

  static readonly DEFAULT_PAGE_SIZE = 10;

  static createDefaultPaginator(): Page<any> {
    return {
      elements: [],
      pageNumber: 0,
      pageSize: PaginationHelper.DEFAULT_PAGE_SIZE,
      totalElements: 0,
      totalPages: 0,
    };
  }

}

export interface IResponsePageableList {
  content: any[];
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
}
