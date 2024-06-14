import { Component, ElementRef } from '@angular/core';
import { LayoutService } from "./service/app.layout.service";
import versionFile from "../../version.json";

@Component({
    selector: 'app-sidebar',
    templateUrl: './app.sidebar.component.html'
})
export class AppSidebarComponent {

    versionInfo: any = (versionFile as any);

    constructor(public layoutService: LayoutService, public el: ElementRef) { }
}

