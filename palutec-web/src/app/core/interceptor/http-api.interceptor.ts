import { catchError, map, tap } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { ToastService } from '../common/template/toast/toast.service';
import { MessageService } from 'primeng/api';

@Injectable()
export class HttpApiInterceptor implements HttpInterceptor {

  
  constructor(
    private messageService: ToastService
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .pipe(
        tap((event) => {
          if (event instanceof HttpResponse) {
            let dataBody = event.body?.data;
            return event.clone({ body: dataBody })
          }
          return event;
        }),
        catchError((error: HttpErrorResponse) => {
          let errorMessage = '';

          if (error.error?.data?.detail) {
            // this.messageService.add({ key: 'tst', severity: 'error', summary: 'Erro', detail: error.error?.data?.detail });
            this.messageService.showDanger(error.error?.data.detail);
          }

          if (error.error instanceof ErrorEvent) {
            // client-side error
            errorMessage = `Error: ${error.error.message}`;
            console.log(error.error);
          } else if (error?.error?.detail) {
            // api validation
            errorMessage = `${error.error.detail}`;

            if (error.error.objects) {
              error.error.objects.forEach((obj: any) => {
                errorMessage = `${errorMessage}  ${obj.userMessage}`;
              });
            } else {
              if (error.error.detail) {
                var errMessage = error.error.detail;
                // this.toastService.showDanger(errMessage);
              }
            }
          } else {
            // server-side error
            errorMessage = 'Ocorreu um erro interno. Tente novamente mais tarde.';
            console.error(error.error);
          }
          return throwError(() => new Error(errorMessage));
        }),
      );
  }
}
