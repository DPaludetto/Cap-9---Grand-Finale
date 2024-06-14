import { Clipboard } from '@angular/cdk/clipboard';
import { Component, ContentChildren, ElementRef, Input, QueryList, TemplateRef, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ToastService } from '../toast/toast.service';

@Component({
  selector: 'app-picklist',
  templateUrl: './picklist.component.html',

})
export class PicklistComponent {
  @Input() parentForm = new FormGroup({});

  @Input() sourceHeader = 'Disponíveis';

  @Input() targetHeader = 'Selecionados';

  @Input() filterBy = 'uiDescription';

  @Input() source!: any[];

  @Input() target!: any[];

  @Input() disabled = false;

  @Input() onMoveToTarget?: any;
  @Input() onMoveToSource?: any;

  @Input() showCopy = true;
  
  constructor(private clipboard: Clipboard,
              private toast: ToastService){

  }

  disableComponent(): boolean {
    return this.parentForm.disabled;
  }

  clipboardCopyTarget(): void{
    var content = '';
    this.target?.forEach(e =>{
      content += e.feppId + '\t' + e.name + '\n';
    });
    this.clipboard.copy(content);
    this.toast.showStandard('Conteúdo copiado para área de transferência.');
  }

 

}
