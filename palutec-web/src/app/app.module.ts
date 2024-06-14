import { NgModule } from '@angular/core';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AppLayoutModule } from './layout/app.layout.module';
import { NotfoundComponent } from './demo/components/notfound/notfound.component';
import { ProductService } from './demo/service/product.service';
import { CountryService } from './demo/service/country.service';
import { CustomerService } from './demo/service/customer.service';
import { EventService } from './demo/service/event.service';
import { IconService } from './demo/service/icon.service';
import { NodeService } from './demo/service/node.service';
import { PhotoService } from './demo/service/photo.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpApiInterceptor } from './core/interceptor/http-api.interceptor';
import { MessageService } from 'primeng/api';
import { FormsModule } from '@angular/forms';
import { Auth0Module } from './layout/auth/auth.module';
import { DialogService } from 'primeng/dynamicdialog';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';

@NgModule({
    declarations: [
        AppComponent, NotfoundComponent,
    ],
    imports: [
        AppRoutingModule,
        AppLayoutModule,
        FormsModule,
        Auth0Module
        
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: HttpApiInterceptor, multi: true, },
        //{ provide: LocationStrategy, useClass: HashLocationStrategy },
        DialogService,
        RxFormBuilder,
        CountryService, 
        CustomerService, 
        EventService, 
        IconService, 
        NodeService,
        PhotoService, 
        ProductService,
        
        
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
