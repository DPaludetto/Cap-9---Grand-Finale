import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Observable, map, tap } from "rxjs";
import { ICrudComponent, ICrudService, ICrudViewComponent, OpenMode } from "./crud.service.obj";
import Page, { IResponsePageableList, PaginationHelper } from "./pagination";
import { AppComponent } from "@src/app/app.component";
import { environment } from 'src/environments/environment';
import { URLBuilder } from "../../utils/url.builder";
import { DateUtils } from "../../utils/date-utils";
import * as FileSaver from "file-saver";


export abstract class AbstractCrudList<T, V extends ICrudViewComponent, S extends ICrudService<T>> implements ICrudComponent<T>{


    constructor(protected _service: S, protected _viewerComponent: V){
        
    }
    public get service(): S{
        return this._service;
    }
    // getService(): S {
    //     return this.service;
    // }
    openToEdit(item: any): void {
        //alert('openToEdit: Não implementado.');
        this.openItem(item, OpenMode.EDIT);
    }
    openToCreate(item: any, additional?: any): void {
        // alert('openToCreate: Não implementado.');
        this.openItem(item, OpenMode.NEW);
    }
    openToView(item: any): void {
        //alert('openToView: Não implementado.');
        this.openItem(item, OpenMode.VIEW);
    }

    openItem(item: any, mode: OpenMode){
        this._viewerComponent.openItem(item, mode);
    }


}

export abstract class AbstractCrudService<T> implements ICrudService<T>{ 


    protected http: HttpClient;

    protected httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
      };

    constructor(protected serviceUrl: string){
        this.serviceUrl = `${environment.apiUrl}/`+serviceUrl;
        this.http = AppComponent.injector.get(HttpClient);
    }

    list(filter?: any): Observable<any> {
        let page = PaginationHelper.createDefaultPaginator();
        page.filter = filter;
        return this.listPage(page);
    }


    listPage(page: Page<T>): Observable<any> {
        const url = URLBuilder.new(this.serviceUrl)
        .addUrlParam('page', page.pageNumber)
        .addUrlParam('size', page.pageSize)
        .addUrlParam('sort', page.sort || 'createdAt,desc')
        // .addUrlParam('id', filter?.id)
        // .addUrlParam('description', filter?.description)
        if(page?.filter){
            Object.keys(page?.filter).forEach(k => {
                url.addUrlParam(k, page.filter[k])
            });
        }
        
        return this.http.get<IResponsePageableList>(url.build(), this.httpOptions)
            .pipe(
                tap((response: IResponsePageableList) => response.content.map((item) => this.convertBeanDates(item))),
                tap((response: IResponsePageableList) => response.content.map((item) => this.convertData(item)))
            );
    }

    getById(id: any): Observable<T> {
        console.log('getID');
        return this.http.get<T>(`${this.serviceUrl}/${id}`, this.httpOptions);
    }
    create(type: T): Observable<T> {
        console.log('create');
        return this.http.post<T>(this.serviceUrl, type);
    }
    update(type: T | T[]): Observable<T> {
        console.log('update');
        return this.http.put<T>(this.serviceUrl, type, this.httpOptions);
    }
    delete(id: any): Observable<any> {
        console.log('delete');
        return this.http.delete<T>(`${this.serviceUrl}/${id}`, this.httpOptions);
    }

    searchByText(text: string, fields: string[]): Observable<any>{
        
        const url = URLBuilder.new(`${this.serviceUrl}/text-search`)
            .addUrlParam('size', 10)
            .addUrlParam('fields', fields)
            .addUrlParam('text', text)
            .addUrlParam('strategy', 'FUZZY')
            // .addUrlParam('sort', 'updatedAt,desc')
            // .addUrlParam('id', filter?.id)
            // .addUrlParam('description', filter?.description)
            .build();

        return this.http.get<IResponsePageableList>(url, this.httpOptions)
            .pipe(
                tap((response: IResponsePageableList) => response.content.map((item) => this.convertBeanDates(item))),
                tap((response: IResponsePageableList) => response.content.map((item) => this.convertData(item)))
            );

    }
   
    protected convertBeanDates(from: any){
        from.createdAt = DateUtils.dateToStringFullFormatPresentation(from?.createdAt);
        from.updatedAt = DateUtils.dateToStringFullFormatPresentation(from?.updatedAt);
    }
    convertData(from: any){
    }

    exportData(filter?: any): void {

        const url = URLBuilder.new(`${this.serviceUrl}/export-data`)
        if(filter){
            Object.keys(filter).forEach(k => {
                url.addUrlParam(k, filter[k])
            });
        }
        const header = new HttpHeaders({ 'Content-Type': 'application/JSON' });
        this.http.get<any>(url.build(), 
            { headers: header, responseType: 'blob' as 'json', observe: 'response' as 'body' })
            .subscribe((response: HttpResponse<any>) => {
                var name = response.headers.get('Download-FileName');
                FileSaver.saveAs(response.body, name as string);
              });
    }


}

