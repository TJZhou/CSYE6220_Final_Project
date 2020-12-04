import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private jwtHelper: JwtHelperService) { }

  public isAuthenticated(): boolean {
    const token = this.jwtHelper.tokenGetter();
    // Check whether the token is expired and return
    if (this.jwtHelper.isTokenExpired(token)) {
      localStorage.removeItem('access_token');
      return false;
    } else {
      return true;
    }
    // return !this.jwtHelper.isTokenExpired(token);
  }
}
