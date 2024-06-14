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

export class Product extends  AbstractAppObject{
    
    @prop() name?: string;
    @prop() type?: string;
    @prop() description?: string;
    @prop() brand?: string;
    @prop() price?: string;
    @prop() stockQuantity?: string;

}


export class Contrato extends AbstractAppObject{
    @prop() ativo?: boolean;
    @prop() numeroContrato?: string;
    @prop() tipoContrato?: string;
    @prop() empresa?: string;
    
    @prop() descricaoFluxo?: string;
    @prop() denominacaoTipoContrato?: string;
    @prop() status?: string;
    @prop() centroCustoSAP?: string;
    @prop() fornecedor?: string;
    @prop() responsavel?: string;
    @prop() contratoAntigo?: string;

    despesas?: Despesa[];

    
}

export class Despesa extends AbstractAppObject{
    codigoContrato?: string;
    codigoFornecedor?: string;
    descricaoFluxo?: string;
    enderecoImovel?: string;
    tipoMovimento?: string;
    tipoDespesa?: string;
    descricao?: string;
    ocrTokens?: string;
    tipoMovimentoSap?: string;
    tipoCondicaoSap?: string;
    valorSap?: number;
    referenciaBoleto?: string;
    centroCusto?: string;
    isDefault?: boolean;
    grupoResolucao?: string;
    pagamentos?: Pagamento[];
}


export class Pagamento extends AbstractAppObject{

    @prop() documentoOcrFileName?: string;
    @prop() emissao?: string; 
    @prop() valor?: number;
    @prop() cedente?: string; 
}

export class Boleto extends Pagamento{
    
    @prop() multa?: string; 
    @prop() numeroContrato?: string;
    @prop() denominacaoTipoContrato?: string;
    @prop() vencimento?: string;
    @prop() empresaContrato?: string; 
    @prop() statusBoleto?: string;
    @prop() cpfCnpjCedente?: string;
    @prop() contratoDespesaId?: string;
    documentoOcrFileId?: string;
    contratoDespesa?: ContratoDespesa;
    statusVencimentoBoleto?: StatusVencimentoBoleto;
}

export class ContratoDespesa extends AbstractAppObject{
    
    codigoContrato?: string;
    codigoFornecedor?: string;
    descricaoFluxo?: string;
    enderecoImovel?: string;
}

export class StatusVencimentoBoleto {
    public static readonly LATE = new StatusVencimentoBoleto('LATE', 'Vencimento em 2 dias.', 'badge status-error'); 
    public static readonly WARN = new StatusVencimentoBoleto('WARN', 'Perto do vencimento em 10 dias.', 'badge status-s1') 
    public static readonly NORMAL = new StatusVencimentoBoleto('NORMAL', 'Vencimento em +30 dias.', 'badge status-success');
    public static readonly NO_DATE = new StatusVencimentoBoleto('NO_DATE', 'Sem data de vencimento', '');

    constructor(public name: string, public description: string, public cssClass: string){}

    public static all = [
        StatusVencimentoBoleto.LATE,
        StatusVencimentoBoleto.WARN,
        StatusVencimentoBoleto.NORMAL,
        StatusVencimentoBoleto.NO_DATE
    ];

    static fromName(name: any): StatusVencimentoBoleto{
        return StatusVencimentoBoleto.all.find(e => e.name === name) as StatusVencimentoBoleto;
    }
}

export class StatusBoleto {
    public static readonly NEW = new StatusBoleto('NEW', 'Novo', 'badge status-primary'); 
    public static readonly NO_DATA_FILLED = new StatusBoleto('NO_DATA_FILLED', 'Dados não preenchidos', 'badge status-s1');
    public static readonly DESPESA_MATCHED = new StatusBoleto('DESPESA_MATCHED', 'Despesa encontrada', 'badge status-success');
    public static readonly ALL_DATA_FILLED = new StatusBoleto('ALL_DATA_FILLED', 'Todos os dados preenchidos', 'badge status-s2');
    public static readonly DESPESA_MORE_THAN_ONE = new StatusBoleto('DESPESA_MORE_THAN_ONE', 'Mais de uma despesa', 'badge status-s4');
    public static readonly PARTIAL_DATA_FILLED = new StatusBoleto('PARTIAL_DATA_FILLED', 'Dados parcialmente preenchidos', 'badge status-s3');
    public static readonly DESPESA_NO_MATCHED = new StatusBoleto('DESPESA_NO_MATCHED', 'Nenhuma despesa encontrada', 'badge status-s4');
    public static readonly ERROR = new StatusBoleto('ERROR', 'Erro', 'badge status-error');

    public static all = [
        StatusBoleto.NEW,
        StatusBoleto.NO_DATA_FILLED,
        StatusBoleto.DESPESA_MATCHED,
        StatusBoleto.ALL_DATA_FILLED,
        StatusBoleto.DESPESA_MORE_THAN_ONE,
        StatusBoleto.PARTIAL_DATA_FILLED,
        StatusBoleto.DESPESA_NO_MATCHED,
        StatusBoleto.ERROR
    ];

    name!: string;
    description!: string;
    cssClass!: string;

    constructor(name: string, description: string, cssClass: string){
        this.name = name;
        this.description = description;
        this.cssClass = cssClass;
    }

    static fromName(name: any): StatusBoleto{
        return StatusBoleto.all.find(e => e.name === name) as StatusBoleto;
    }
}

export class Pessoa extends AbstractAppObject{
    nome?: string;
    cpfCnpj?: string;
}

export class DocumentoOcr extends AbstractAppObject{
    nomeArquivo?: string;
    status?: string;
    tipoDocumento?: string;
    origem?: string;   
}

export class StatusDocumentoOcr {
    public static readonly NEW = new StatusDocumentoOcr('NEW', 'Novo', 'badge status-primary'); 
    public static readonly PARSED = new StatusDocumentoOcr('PARSED', 'OCR realizado', 'badge status-s1');
    public static readonly DOCUMENT_TYPE_DETECTED = new StatusDocumentoOcr('DOCUMENT_TYPE_DETECTED', 'Tipo detectado', 'badge status-s2');
    public static readonly DOCUMENT_ROUTED = new StatusDocumentoOcr('DOCUMENT_ROUTED', 'Roteado', 'badge status-success');
    public static readonly ERROR = new StatusDocumentoOcr('ERROR', 'Erro', 'badge status-error');
    public static readonly DUPLICATED = new StatusDocumentoOcr('DUPLICATED', 'Arquivo Duplicado', 'badge status-error');

    public static all = [
        StatusDocumentoOcr.NEW,
        StatusDocumentoOcr.PARSED,
        StatusDocumentoOcr.DOCUMENT_TYPE_DETECTED,
        StatusDocumentoOcr.DOCUMENT_ROUTED,
        StatusDocumentoOcr.ERROR,
        StatusDocumentoOcr.DUPLICATED,

    ];

    name!: string;
    description!: string;
    cssClass!: string;

    constructor(name: string, description: string, cssClass: string){
        this.name = name;
        this.description = description;
        this.cssClass = cssClass;
    }

    static fromName(name: any): StatusDocumentoOcr{
        return StatusDocumentoOcr.all.find(e => e.name === name) as StatusDocumentoOcr;
    }
}

export class Usuario extends AbstractAppObject{

    username?: string;
    name?: string;
    mail?: string;
    status?: string;
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

export class GrupoResolucao extends AbstractAppObject{
    nome?: string;
    descricao?: string;
    usuarios?: Usuario[];
}

