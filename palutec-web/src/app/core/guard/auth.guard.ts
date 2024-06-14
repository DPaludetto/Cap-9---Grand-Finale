import {
  CanActivate, Router,
} from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private auth: AuthService,
    private router: Router,
  ) { }

  canActivate(): Observable<boolean> | Promise<boolean> | boolean {
    if (!this.auth.isAccessGranted()) {
      return this.auth.getNewAccessToken()
        .then(() => true, () => false);
    }
    return true;
  }
}
