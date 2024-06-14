import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRoutingModule } from './auth-routing.module';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';


const routes: Routes = [
    { path: '/login-2', component: LoginComponent },
 
    
  ];

@NgModule({
    imports: [
        CommonModule,
        AuthRoutingModule,
        RouterModule.forChild(routes),
        
    ]
})

export class AuthModule { }
