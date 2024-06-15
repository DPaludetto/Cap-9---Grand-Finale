import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from '@src/app/core/common/template/toast/toast.service';
import { AuthService } from '@src/app/core/service/auth.service';
import { LayoutService } from 'src/app/layout/service/app.layout.service';

@Component({
    selector: 'app-login-0',
    templateUrl: './login.component.html',
    styles: [`
        :host ::ng-deep .pi-eye,
        :host ::ng-deep .pi-eye-slash {
            transform:scale(1.6);
            margin-right: 1rem;
            color: var(--primary-color) !important;
        }
    `]
})
export class Login0Component {

    valCheck: string[] = ['remember'];

    password!: string;
    user!: string;

    loginForm: FormGroup;

    errorMessage!: string;

    constructor(
            private layoutService: LayoutService,
            private formBuilder: FormBuilder,
            private toastService: ToastService,
            private router: Router,
            private auth: AuthService,) { 

        this.loginForm = formBuilder.group({
            user: ['', [Validators.required]],
            password: ['', [Validators.required]],
          });
    }

    login(): void {
        // if (this.loginForm.valid) {
        this.auth.login(this.user, this.password)
            .then(() => {
              this.router.navigateByUrl('/biz/cadastro-list');
            })
            .catch((error) => {
                this.errorMessage = 'Usuário ou senha incorretos.';
            //   this.toastService.showDanger('Usuário ou senha incorreto!');
              //this.errorHandler.handle(error);
            });
        }
    //   }
}
