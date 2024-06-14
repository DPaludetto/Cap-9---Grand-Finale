import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-page-section-template',
  template: `
          <div class="container">
            <div class="mb-20" *ngIf="pageTitle">
              <span class="title">{{ pageTitle }}</span>
              <span class="description" *ngIf="pageDescription">{{ pageDescription }}</span>
            </div>
            <hr>
            <ng-content class="page-content" select="[page-content]"></ng-content>
            <ng-content class="page-footer" select="[page-footer]"></ng-content>
          </div>
  `,

})
export class PageSectionTemplateComponent {
  @Input() pageTitle: string;

  @Input() pageDescription: string;

  constructor() {
    this.pageTitle = '';
    this.pageDescription = '';
  }
}
