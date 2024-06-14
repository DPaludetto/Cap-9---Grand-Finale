/* eslint-disable @typescript-eslint/dot-notation */
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { environment } from '../../../environments/environment';
import { IJwtPayload } from './jwtPayload';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastService } from '../common/template/toast/toast.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  oauthTokenUrl = environment.authApiUrl;

  jwtPayload?: IJwtPayload  ;

  private toastService!: ToastService;

  constructor(
    private http: HttpClient,
    private jwtHelper: JwtHelperService,
    private router: Router,
  ) {
    this.uploadToken();
  }

  login(username: string, password: string): Promise<void> {
    const headers = this.createHeader();

    const httpParams = new URLSearchParams();
    httpParams.append('username', username);
    httpParams.append('password', password);
    httpParams.append('grant_type', 'password');

    return this.http.post(this.oauthTokenUrl, httpParams.toString(), { headers })
      .toPromise()
      .then((response: any) => {
        this.storeToken(response);
      })
      .catch((response) => {
        if (response.status === 400) {
          //if (response.error === 'invalid_grant') {
            return Promise.reject(new Error('Usuário ou senha incorreto!'));
          //}
        }
        return Promise.reject(response);
      });
  }

  public logout(){
    this.clearAccessToken();
    this.router.navigateByUrl('/login');
  }


  getNewAccessToken(): Promise<any> {
    if (this.isTokenInvalid('refresh_token')) {
      this.router.navigateByUrl('/login');
      this.clearAccessToken();
      return Promise.resolve(null);
    }

    const headers = this.createHeader();

    const body = `grant_type=refresh_token&refresh_token=${localStorage.getItem('refresh_token')}`;
    return this.http.post(this.oauthTokenUrl, body,
      { headers, withCredentials: true })
      .toPromise()
      .then((response: any) => {
        this.storeToken(response['access_token']);
        localStorage.setItem('refresh_token', response['refresh_token']);
        return Promise.resolve(null);
      })
      .catch(() => {
        this.toastService.showDanger('Sessão expirada');
        this.router.navigateByUrl('/login');
        Promise.resolve(null);
      });
  }

  clearAccessToken(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('refresh_token');
    this.jwtPayload = undefined;
  }

  isAccessGranted(): boolean {
    return !this.isTokenInvalid('token');
  }

  private isTokenInvalid(tokenKey: string): boolean {
    const token = localStorage.getItem(tokenKey);
    return !token || token === 'undefined' || this.jwtHelper.isTokenExpired(token);
  }

  havePermission(permission: string): undefined | boolean {
    return this.jwtPayload && this.jwtPayload?.authorities?.includes(permission);
  }

  haveAnyPermission(roles?: string[]): boolean {
    return roles ? roles.some((role) => this.havePermission(role)) : true;
    // TODO rever quando todos usuários tiverem role
  }

  getUserInfo(): IJwtPayload | undefined{
    return this.jwtPayload;
  }

  getNameUser(): string | undefined {
    return this.jwtPayload?.fullName;
  }

  getUserId(): string | undefined {
    return this.jwtPayload?.userId;
  }

  getProfile(): string | undefined {
    return this.jwtPayload?.authorities?.[0];
  }

  inProfiles(profiles: string[]): boolean{
    return profiles.includes(this.getProfile()+'')
  }

  getUserGroupName(): string | undefined {
    return this.jwtPayload?.groupName;
  }

  getUserGroupId(): string | undefined {
    return this.jwtPayload?.groupId;
  }

  getPrincipalArea(): string | undefined {
    return this.jwtPayload?.principalAreaName;
  }

  getPrincipalAreaId(): string | undefined {
    return this.jwtPayload?.principalAreaId;
  }

  private storeToken(response: any) {
    this.jwtPayload = this.jwtHelper.decodeToken(response['access_token']) || undefined;
    localStorage.setItem('token', response['access_token']);
    localStorage.setItem('refresh_token', response['refresh_token']);
  }


  private uploadToken() {
    const token = localStorage.getItem('token');

    if(this.isAccessGranted()){
      return;
    }

    if (token && token !== 'undefined') {
      this.storeToken(token);
    }
  }

  private createHeader(): HttpHeaders {
    return new HttpHeaders()
      .set('Content-Type', 'application/x-www-form-urlencoded')
      .set('Authorization', environment.usernamePasswordApi);
  }
}
