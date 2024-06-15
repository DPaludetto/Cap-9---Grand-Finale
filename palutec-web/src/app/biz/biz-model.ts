import { FormGroup } from "@angular/forms";
import { AppComponent } from "../app.component";
import { RxFormBuilder, prop } from '@rxweb/reactive-form-validators';
import { DateUtils } from "../core/utils/date-utils";

export abstract class AbstractAppObject{
    id?: string;
    @prop() createdAt?: Date;
    @prop() updatedAt?: Date;
    @prop() createdBy?: string;

    formGroup?: FormGroup;
    
    // getFormGroup(): FormGroup{
    //     if(this.formGroup === undefined){
    //       var formBuilder = AppComponent.injector.get(RxFormBuilder);
    //       this.formGroup = formBuilder.formGroup(this);
    //     }
    //     return this.formGroup;
    // }

    get auditInfo(): string{
        return `Criado em: ${DateUtils.dateToStringFullFormatPresentation(this.createdAt)}. 
        Alterado em: ${DateUtils.dateToStringFullFormatPresentation(this.updatedAt)}`;
    }
}


export class Usuario extends AbstractAppObject{

    username?: string;
    name?: string;
    mail?: string;
    status?: string;
}

export class Produto extends AbstractAppObject{
    @prop() name?: string;
    @prop() type?: string;
    @prop() description?: string;
    @prop() brand?: string;
    @prop() price?: string;
    @prop() stockQuantity?: string;
}


export class Pessoa extends AbstractAppObject{
    nome?: string;
    cpfCnpj?: string;
}


export class UserStatus {
    public static readonly ACTIVE = new UserStatus('ACTIVE', 'Ativo', 'badge status-success'); 
    public static readonly BLOCKED = new UserStatus('BLOCKED', 'Bloqueado', 'badge status-error');

    public static all = [
        UserStatus.ACTIVE,
        UserStatus.BLOCKED,
    ];

    name!: string;
    description!: string;
    cssClass!: string;

    constructor(name: string, description: string, cssClass: string){
        this.name = name;
        this.description = description;
        this.cssClass = cssClass;
    }

    static fromName(name: any): UserStatus{
        return UserStatus.all.find(e => e.name === name) as UserStatus;
    }
}


