
import { Injectable } from "@angular/core";
import { DocumentoOcr } from "../biz-model";
import { AbstractCrudService } from "@src/app/core/service/crud/crud-impl.component";
import { HttpHeaders, HttpResponse } from "@angular/common/http";
import * as FileSaver from "file-saver";
import { FileRepositoryService } from "../file-repository.service";
import { Observable, tap } from "rxjs";
import { IResponsePageableList } from "@src/app/core/service/crud/pagination";


@Injectable({
    providedIn: 'root',
})
export class DocumentoOcrCrudService extends AbstractCrudService<DocumentoOcr>{




    constructor( private fileRepository: FileRepositoryService){
        super('documento-ocr');
    }

    reprocessDocument(id: string){
        this.http.put<DocumentoOcr>(this.serviceUrl+'/reprocess/'+id, id, this.httpOptions)
            .subscribe();
    }

    downloadDocument(document: any) {
        this.fileRepository.download(document.fileId);
    }

    setDocumentType(id: any, type: string) {
        this.http.put<DocumentoOcr>(`${this.serviceUrl}/set-type/${type}/${id}`, id, this.httpOptions)
            .subscribe();
    }

    loadProcessLog(id: string | undefined) : Observable<any> {

        return this.http.get<any>(`${this.serviceUrl}/history/${id}`, this.httpOptions)
            .pipe(
                tap((response) => response.map((item: any) => this.convertBeanDates(item))),
                tap((response) => response.map((item: any) => this.convertData(item)))
            );
    }

}

