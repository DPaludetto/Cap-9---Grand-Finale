
import { Injectable } from "@angular/core";
import { Contrato } from "../biz-model";
import { AbstractCrudService } from "@src/app/core/service/crud/crud-impl.component";
import { Observable, tap } from "rxjs";
import { IResponsePageableList } from "@src/app/core/service/crud/pagination";


@Injectable({
    providedIn: 'root',
})
export class ContratoCrudService extends AbstractCrudService<Contrato>{

    constructor(){
        super('contrato');
    }

    getContratoDespesaByContratoId(idContrato: any) : Observable<any>{
        return this.http.get(`${this.serviceUrl}/${idContrato}/despesas`, this.httpOptions)
        .pipe(
            tap((response: any) => response.map((item: any) => this.convertBeanDates(item))),
            tap((response: any) => response.map((item: any) => this.convertData(item)))
        );
    }

}
