import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {
        this.model = [
            // {
            //     label: 'Home',
            //     items: [
            //         { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/home'] }
            //     ]
            // },
            {
                label: 'Produto',
                items: [
                    { label: 'Cadastro', icon: 'pi pi-fw pi-list', routerLink: ['/biz/cadastro-list'] }
                ]
            },            
            {
                label: 'Administração',
                items: [
                    { label: 'Usuários', icon: 'pi pi-fw pi-user', routerLink: '/biz/adm/usuario-list'},
                ]
            },            
           
        ];
    }
}
