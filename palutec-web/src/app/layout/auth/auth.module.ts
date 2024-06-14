import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Login0Component } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { Routes, RouterModule } from '@angular/router';
import { JwtHelperService, JwtModule } from '@auth0/angular-jwt';
import { environment } from '../../../environments/environment';


const routes: Routes = [
  { path: 'login', component: Login0Component },
];

const tokenGetter = () => localStorage.getItem('token');

@NgModule({
  declarations: [
    Login0Component,
  ],
  imports: [
    CommonModule,
    ButtonModule,
    CheckboxModule,
    InputTextModule,
    FormsModule,
    PasswordModule,
    JwtModule.forRoot({
      config: {
        tokenGetter,
        allowedDomains: environment.allowedDomains,
        disallowedRoutes: [environment.authApiUrl],
      },
    }),    
    RouterModule.forChild(routes),
  ],
  providers: [
    JwtHelperService,
  ],
})
export class Auth0Module { }
