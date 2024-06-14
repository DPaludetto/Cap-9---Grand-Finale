import { Injectable } from "@angular/core";
import { GrupoResolucao } from "@src/app/biz/biz-model";
import { AbstractCrudService } from "@src/app/core/service/crud/crud-impl.component";


@Injectable({
    providedIn: 'root',
})
export class GrupoResolucaoCrudService extends AbstractCrudService<GrupoResolucao>{

    constructor(){
        super('grupo-resolucao');
    }
}