export default interface Pagination {
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export class PaginationHelper {

  static createDefaultPaginator(): Pagination {
    return {
      number: 0,
      size: 10,
      totalElements: 0,
      totalPages: 0,
    };
  }

  static createFullSizePaginator(): Pagination {
    return {
      number: 0,
      size: 10,
      totalElements: 0,
      totalPages: 0,
    };
  }  

}