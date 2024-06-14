import { Component, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BooleanUtils } from '@src/app/core/utils/boolean-utils';
import { Clipboard } from '@angular/cdk/clipboard';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-form-input',
  templateUrl: './form-input.component.html',

})
export class FormInputComponent {
  @Input() parentForm: FormGroup = new FormGroup({});

  @Input() name = '';

  @Input() label = '';

  @Input() id = '';
  
  @Input() mode = 'text';

  @Input() addonIcon = '';

  @Output() onAddonClick = new EventEmitter();

  mask = '';

  canCopyContent: any;
  input: any;

  constructor(private clipboard: Clipboard){
    this.input = this.parentForm.controls[this.name];
  }

  get copyable(): boolean{
    return true; //BooleanUtils.isTrue(this.canCopyContent);
  }

  cpfcnpjmask() {
    const value = this.input.value;
    
    if(value.length <= 14) {
      this.mask = '00.000.000/0000-00'
    }
    else {
      this.mask = '00.000.0000-00'
    }
  }

  toClipboard(): void{
    var content = this.input.value;
    this.clipboard.copy(content);
    // this.toast.showStandard('Conteúdo copiado para área de transferência.');
  }

}
