import { Component, Inject, Injectable, Input, OnInit } from '@angular/core';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root',
})
@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  providers: [DialogService]
})
export class FileUploadComponent {


  @Input() urlService = `${environment.apiUrl}/file-repository/OCR`;
  @Input() accept = 'image/jpeg,image/png,image/gif,application/pdf';

  dialogRef?: DynamicDialogRef;

  constructor(private modal: DialogService){
  }

  open(title: string, urlEndpointService: string, width = '40%', height = '70%'){
    this.urlService = urlEndpointService;
    this.dialogRef = this.modal.open(FileUploadComponent, {
      header: title,
      width: width,
      height: height,
      contentStyle: { overflow: 'auto' },
      baseZIndex: 10000,
      maximizable: true,
      closeOnEscape: true,
      data: {
        
      },
    });
  }

  onUpload($event: any) {
 
  }
}