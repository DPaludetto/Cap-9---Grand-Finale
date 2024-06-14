import { JwtHelperService } from '@auth0/angular-jwt';

export class TokenUtils {
  static getUserId(): number {
    const helper = new JwtHelperService();

    const decodedToken = helper.decodeToken(localStorage.getItem('token') || '');

    return decodedToken?.usuario_id;
  }

  static getRegistryDate(): string {
    const helper = new JwtHelperService();

    const decodedToken = helper.decodeToken(localStorage.getItem('token') || '');

    return decodedToken?.data_registro;
  }

  static getEndDate(): string {
    const helper = new JwtHelperService();

    const decodedToken = helper.decodeToken(localStorage.getItem('token') || '');

    return decodedToken?.data_termino;
  }
}
