
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import * as FileSaver from "file-saver";
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root',
})
export class FileRepositoryService {
    
    constructor(private http: HttpClient){
    
    }

    download(id: any) {
        const url = `${environment.apiUrl}/file-repository/${id}`;
        const header = new HttpHeaders({ 'Content-Type': 'application/JSON' });
        this.http.get<any>(url, 
            { headers: header, responseType: 'blob' as 'json', observe: 'response' as 'body' })
            .subscribe((response: HttpResponse<any>) => {
                var name = response.headers.get('Download-FileName');
                FileSaver.saveAs(response.body, name as string);
              });
    }

}

