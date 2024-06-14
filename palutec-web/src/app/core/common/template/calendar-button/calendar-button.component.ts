import { MAT_DATE_FORMATS } from '@angular/material/core';
import {
  Component, forwardRef, Host, Input, OnInit, Optional, Output, SkipSelf, EventEmitter,
} from '@angular/core';
import { NG_VALUE_ACCESSOR, FormControl, ControlContainer } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';

export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-calendar-button',
  templateUrl: './calendar-button.component.html',
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
    {
      provide: NG_VALUE_ACCESSOR,
      // eslint-disable-next-line @typescript-eslint/no-use-before-define
      useExisting: forwardRef(() => CalendarButtonComponent),
      multi: true,
    }],
})
export class CalendarButtonComponent implements OnInit {
  @Input() formControlName = '';

  @Output() changeDate = new EventEmitter();

  control: FormControl = new FormControl('');

  disabled = false;

  constructor(
    @Optional() @Host() @SkipSelf()
    private controlContainer: ControlContainer,
  ) { }

  onChange: any = () => { };

  onTouch: any = () => {};

  change(event: MatDatepickerInputEvent<any, any>): void {
    this.onChange(event.value);
    this.changeDate.emit(event.value);
  }

  ngOnInit(): void {
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control?.get(this.formControlName) as FormControl;
    }
  }

  writeValue(value: any): void {
    this.control?.setValue(value);
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
}
