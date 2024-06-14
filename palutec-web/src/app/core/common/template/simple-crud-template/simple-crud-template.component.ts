// import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  Component, EventEmitter, Input, Output, OnInit,
} from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CRUD_LIST } from './crud-mode';


@Component({
  selector: 'app-simple-crud-template',
  templateUrl: './simple-crud-template.component.html',

})
export class SimpleCrudTemplateComponent implements OnInit {
  @Input() pageTitle = '';

  @Input() identification?: string;

  @Input() parentForm!: FormGroup;

  @Input() crudUrl!: string;

  @Output() create = new EventEmitter();

  @Output() remove = new EventEmitter();

  @Output() save = new EventEmitter();

  mode: string;

  closeResult?: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    // private modalService: NgbModal,
  ) {
    this.mode = this.route.snapshot.paramMap.get('mode') || 'DETAIL';
  }

  ngOnInit(): void {
    if (this.mode === 'DETAIL') {
      this.parentForm.disable();
    } else {
      this.parentForm.enable();
    }
  }

  createItem(): void {
    this.create.emit();
  }

  removeItem(): void {
    this.remove.emit();
  }

  saveItem(): void {
    this.save.emit();
  }

  cancel(): void {
    this.back();
  }

  back(): void {
    this.router.navigateByUrl(this.crudUrl);
  }

  changeToEditMode(): void {
    this.parentForm.enable();
    this.mode = 'EDIT';
  }

  changeToDetailMode(): void {
    this.parentForm.disable();
    this.mode = 'DETAIL';
  }

  get isCreateMode(): boolean {
    return this.mode === 'CREATE';
  }

  get isDetailMode(): boolean {
    return this.mode === 'DETAIL';
  }

  get isEditMode(): boolean {
    return this.mode === 'EDIT';
  }

  get modeTitle(): string {
    return `${this.getModeName(this.mode)}  ${this.pageTitle}`;
  }

  get deleteMessage(): string {
    return `Tem certeza que deseja excluir ${this.identification}?`;
  }

  getModeName(modeType: string): string {
    return CRUD_LIST.find((e) => e.type === modeType)?.name || '';
  }

  open(content: any): void {
    // this.modalService.open(content).result.then((result) => {
    //   if (result === 'accept') {
    //     this.closeResult = result;
    //     this.removeItem();
    //   }
    //   if (result === 'cancel') {
    //     this.cancel();
    //     this.closeResult = result;
    //   }
    // }, (reason) => {
    //   this.closeResult = this.getDismissReason(reason);
    // });
  }

  private getDismissReason(reason: any): string {
    // if (reason === ModalDismissReasons.ESC) {
    //   return 'by pressing ESC';
    // } if (reason === ModalDismissReasons.BACKDROP_CLICK) {
    //   return 'by clicking on a backdrop';
    // }
    // return `with: ${reason}`;
    return '';
  }
}
