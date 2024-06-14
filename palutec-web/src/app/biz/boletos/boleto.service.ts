
import { Injectable } from "@angular/core";
import { Boleto } from "../biz-model";
import { AbstractCrudService } from "@src/app/core/service/crud/crud-impl.component";
import { DateUtils } from "@src/app/core/utils/date-utils";
import { FileRepositoryService } from "../file-repository.service";


@Injectable({
    providedIn: 'root',
})
export class BoletoCrudService extends AbstractCrudService<Boleto>{

    constructor(private fileRepository: FileRepositoryService){
        super('boleto');
    }

    override convertData(from: any){
        from.vencimento = DateUtils.dateToStringFormatPresentation(from?.vencimento);
    }

    downloadDocument(id?: string){
        this.fileRepository.download(id);
    }


}

