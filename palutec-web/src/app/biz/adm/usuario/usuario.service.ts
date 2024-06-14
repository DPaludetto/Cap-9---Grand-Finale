import { AbstractCrudService } from "@src/app/core/service/crud/crud-impl.component";
import { Usuario } from "../../biz-model";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root',
})
export class UsuarioCrudService extends AbstractCrudService<Usuario>{
    
    
    toggleUserStatus(id: any) {
      this.http.put(`${this.serviceUrl}/toggle-status/${id}`, this.httpOptions).subscribe();
    }

    constructor(){
        super('user')
    }
}