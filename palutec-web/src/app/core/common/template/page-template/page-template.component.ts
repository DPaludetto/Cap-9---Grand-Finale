import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { StringUtils } from '@src/app/core/utils/string-utils';

@Component({
  selector: 'app-page-template',
  templateUrl: './page-template.component.html',

})
export class PageTemplateComponent implements OnInit{
  @Input() pageTitle: string;

  @Input() showHeader = false;

  @Input() titleStyle?: any;

  constructor() {
    this.pageTitle = '';
  }
  ngOnInit(): void {
    this.showHeader = StringUtils.isEmpty(this.pageTitle);
  }
}
