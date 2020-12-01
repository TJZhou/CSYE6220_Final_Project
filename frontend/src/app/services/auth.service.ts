import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private jwtHelper: JwtHelperService) { }

  public isAuthenticated(): boolean {
    const token = this.jwtHelper.tokenGetter();
    console.log(token);
    // Check whether the token is expired and return
    return !this.jwtHelper.isTokenExpired(token);
  }
}
