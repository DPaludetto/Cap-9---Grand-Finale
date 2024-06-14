import {
  Component, Input, Optional, SkipSelf, Host, forwardRef, Output, EventEmitter, DoCheck,
} from '@angular/core';
import {
  ControlContainer, ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR,
} from '@angular/forms';


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      // eslint-disable-next-line @typescript-eslint/no-use-before-define
      useExisting: forwardRef(() => CalendarComponent),
      multi: true,
    },
  ],
})
export class CalendarComponent implements DoCheck, ControlValueAccessor {
  @Input() formControlName = '';

  @Input() showOnlyIcon = false;

  @Input() view = 'date';

  @Input() dateFormat = 'dd/mm/yyyy';

  @Input() placeholder = 'dd/mm/aaaa';

  @Input() appendTo = 'body';

  @Input() backward = true;
  @Input() forward = true;

  @Output() changeDate = new EventEmitter();

  control: FormControl = new FormControl('');

  onTouch: any = () => {};

  private today = new Date();
  private oldDate = new Date('01/01/1980');
  private futureDate = new Date('31/12/2049');
 
  constructor(
    @Optional() @Host() @SkipSelf()
    private controlContainer: ControlContainer,
  ) {
  }

  onChange(event: Date): void {
    this.changeDate.emit(event);
  }

  

  ngDoCheck(): void {
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

  get minDate(): Date {
    return this.backward ? this.oldDate : this.today;
  }

  get maxDate(): Date {
    return this.forward ? this.futureDate : this.today;
  }


}
