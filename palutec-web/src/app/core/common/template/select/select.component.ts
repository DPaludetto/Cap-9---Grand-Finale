import {
  Component, Input, Output, EventEmitter,
} from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',

})
export class SelectComponent {
  @Input() items: any[] = [];

  @Input() parentForm: FormGroup = new FormGroup({});

  @Input() fieldName = 'fieldName';

  @Input() bindLabel = 'value';

  @Input() bindValue = 'label';

  @Input() bindValueDisabled = 'disabled';

  @Input() emptyMessage = 'value';

  @Input() searchFn = null;

  @Input() trackByFn = null;

  @Input() filter!: boolean;

  @Output() changeValue = new EventEmitter();

  @Output() clickValue = new EventEmitter();

  constructor() {
    this.trackByFn = this.defaultTrackByFn;
  }

  onChangeValue(event: any): void {
    this.changeValue.emit(event);
  }

  onClick(event: PointerEvent): void {
    this.clickValue.emit(event);
  }

  defaultTrackByFn: any = (item: any): any => item;
}
