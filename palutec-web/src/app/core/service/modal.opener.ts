import { Component, Injectable } from "@angular/core";
import { DialogService } from "primeng/dynamicdialog";


@Injectable()
@Component({
    selector: 'opener',
    template: '',
    providers: [DialogService]
  })
export class DialogOpener {

    constructor(private dialogService: DialogService){

    }

    open( type: any, config: any){
        this.dialogService.open(type, config);
    }
}