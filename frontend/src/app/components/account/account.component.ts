import { JwtHelperService } from '@auth0/angular-jwt';
import { User } from './../../models/user';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
})
export class AccountComponent implements OnInit {
  user: User;
  newPassword: string;
  confirmPassword: string;
  validPassword: RegExp;
  validPhone: RegExp;
  validUsername: RegExp;
  showErrMsgUsername: boolean;
  showErrMsgPassword: boolean;
  showErrMsgConfirmPassword: boolean;
  showErrMsgPhone: boolean;

  constructor(private jwtHelper: JwtHelperService) {
    const token = jwtHelper.decodeToken(localStorage.getItem('access_token'));
    console.log(token);
    this.user = new User(token.name, null, token.email, token.phone);
    this.validUsername = /^[a-zA-Z0-9]+([_\.-]?[a-zA-Z0-9])*$/;
    this.validPhone = /^(\+\d{1,2}\s?)?1?\-?\.?\s?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/;
    this.validPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  }

  ngOnInit(): void {}

  public updateInfo(): void {}

  public updatePassword(): void {}

  public usernameCheck(): boolean {
    // if it's not valid (validate function returns false), then showErrMsgUsername should be true
    this.showErrMsgUsername = !this.validate(this.user.username, this.validUsername);
    return !this.showErrMsgUsername;
  }

  public phoneCheck(): boolean {
    this.showErrMsgPhone = !this.validate(this.user.phone, this.validPhone);
    return !this.showErrMsgPhone;
  }

  public passwordCheck(): boolean {
    this.showErrMsgPassword = !this.validate(this.newPassword, this.validPassword);
    return !this.showErrMsgPassword;
  }

  public confirmPasswordCheck(): boolean {
    if (this.confirmPassword === undefined || this.newPassword !== this.confirmPassword) {
      this.showErrMsgConfirmPassword = true;
      return false;
    } else {
      this.showErrMsgConfirmPassword = false;
      return true;
    }
  }
  private validate(text: string, pattern: RegExp): boolean {
    if (text === undefined || text === null) {
      return false;
    }
    return pattern.test(text);
  }
}
