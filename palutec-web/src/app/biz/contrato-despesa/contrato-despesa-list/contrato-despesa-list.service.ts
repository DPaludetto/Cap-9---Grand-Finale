import { Injectable } from "@angular/core";
import { AbstractCrudService } from "@src/app/core/service/crud/crud-impl.component";
import { Despesa } from "../../biz-model";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class ContratoDespesaCrudService extends AbstractCrudService<Despesa>{
    
    
    constructor(){
        super('contrato-despesa');
    }

}