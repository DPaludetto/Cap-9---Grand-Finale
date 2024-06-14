import { FormGroup, ValidationErrors } from '@angular/forms';
import * as FileSaver from 'file-saver';

export class FormGroupUtils {
  static getErrors(formGroup: FormGroup): string[] {
    const errors: string[] = [];

    Object.keys(formGroup.controls).forEach((key) => {
      const controlErrors: ValidationErrors | null | undefined = formGroup.get(key)?.errors;
      if (controlErrors) {
        Object.keys(controlErrors).forEach((keyError) => {
          errors.push('Control:', key, ', keyError:', keyError, ', err value:', controlErrors[keyError]);
        });
      }
    });
    return errors;
  }

  static toJSON(formGroup: FormGroup): any{
    let jsonValues: any = {};
    let value: any = {};
    Object.keys(formGroup.controls).forEach(key=>{
      value = formGroup.controls[key].value;
      if(value){
        value = value.toJSON ? value.toJSON() : value;
        jsonValues[key] = value;
      }
    });   
    return jsonValues;
  }
}

export class BrowserUtils{

  static saveBase64(base64: string, contentType: string, fileName: string){
    FileSaver.saveAs(new Blob([this.toByte(base64)],  {type: contentType}), fileName);
  }

  private static toByte(base64: string) {
    var binary_string = window.atob(base64);
    var len = binary_string.length;
      var bytes = new Uint8Array(len);
    for (var i = 0; i < len; i++) {
      bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
  }
}

