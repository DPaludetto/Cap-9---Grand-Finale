import 'reflect-metadata';
import { Component, Injector, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { LayoutService } from './layout/service/app.layout.service';
import { IconRegistryService } from './core/service/icon-registry.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {


    public static injector: Injector;

    constructor(
        private primengConfig: PrimeNGConfig, 
        private injector: Injector, 
        private layoutService: LayoutService, 
        private iconRegistryService: IconRegistryService,) { 

        AppComponent.injector = this.injector;
        this.iconRegistryService.init();
    }

    ngOnInit() {


        this.primengConfig.ripple = true;       //enables core ripple functionality

        //optional configuration with the default configuration
        // this.layoutService.config = {
        //     ripple: false,                      //toggles ripple on and off
        //     inputStyle: 'outlined',             //default style for input elements
        //     menuMode: 'static',                 //layout mode of the menu, valid values are "static" and "overlay"
        //     colorScheme: 'light',               //color scheme of the template, valid values are "light" and "dark"
        //     theme: 'lara-light-indigo',         //default component theme for PrimeNG
        //     scale: 12                           //size of the body font size to scale the whole application
        // };

        // this.layoutService.config.scale = 12;
    }
}
