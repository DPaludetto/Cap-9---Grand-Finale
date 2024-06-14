import { ControlValueAccessor, FormGroup, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Input, Component, forwardRef } from '@angular/core';

@Component({
  selector: 'app-input-password',
  templateUrl: './input-password.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      // eslint-disable-next-line @typescript-eslint/no-use-before-define
      useExisting: forwardRef(() => InputPasswordComponent),
    },
  ],
})
export class InputPasswordComponent implements ControlValueAccessor {
  @Input() parentFormGroup: FormGroup = new FormGroup({});

  @Input() fieldName = '';

  @Input() placeholderText = '';

  value = '';

  fieldText = false;

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public onChange = (value: string): void => {};

  public onTouched = (): void => {};

  writeValue(value: string): void {
    this.value = value;
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  toggleFieldTextType(): void {
    this.fieldText = !this.fieldText;
  }

  get fieldTextType(): string {
    return this.fieldText ? 'text' : 'password';
  }

  get showPasswordIcon(): string {
    return this.fieldText ? '/assets/imgs/visible_password.svg' : '/assets/imgs/hidden_password.svg';
  }
}
