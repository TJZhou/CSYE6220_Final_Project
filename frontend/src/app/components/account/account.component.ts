import { UserService } from './../../services/user.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { User } from './../../models/user';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IfStmt } from '@angular/compiler';
@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
})
export class AccountComponent implements OnInit {
  isLoading: boolean;
  user: User;
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
  validPassword: RegExp;
  validPhone: RegExp;
  validUsername: RegExp;
  showErrMsgUsername: boolean;
  showErrMsgPassword: boolean;
  showErrMsgConfirmPassword: boolean;
  showErrMsgPhone: boolean;

  constructor(private jwtHelper: JwtHelperService, private userService: UserService, private errorMessage: MatSnackBar) {
    this.isLoading = false;
    const token = jwtHelper.decodeToken(localStorage.getItem('access_token'));
    this.user = new User(token.username, null, token.email, token.phone);
    this.user.userId = token.aud;
    this.validUsername = /^[a-zA-Z0-9]+([_\.-]?[a-zA-Z0-9])*$/;
    this.validPhone = /^(\+\d{1,2}\s?)?1?\-?\.?\s?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/;
    this.validPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  }

  ngOnInit(): void {}

  public updateInfo(): void {
    if (this.usernameCheck() && this.phoneCheck()) {
      this.isLoading = true;
      this.userService.updateUserInfo(this.user).subscribe(resp => {
        this.isLoading = false;
        alert('Successfully update user information!');
      }, err => {
        this.isLoading = false;
        this.errorMessage.open(err.error.message, 'Err', {
          duration: 5000,
        });
      });
    } else {
      this.errorMessage.open('Please fill all blank fields and check error information', 'Err', {
        duration: 5000,
      });
    }
  }

  public updatePassword(): void {
    if (this.passwordCheck() && this.confirmPasswordCheck()) {
      this.isLoading = true;
      this.userService.updateUserPassword(this.user.userId, this.oldPassword, this.newPassword)
        .subscribe(resp => {
          this.isLoading = false;
          alert('Password updated successfully');
          this.oldPassword = null;
          this.newPassword = null;
          this.confirmPassword = null;
        }, err => {
          this.oldPassword = null;
          this.newPassword = null;
          this.confirmPassword = null;
          this.isLoading = false;
          this.errorMessage.open(err.error.message, 'Err', {
            duration: 5000,
          });
        });
    } else {
      this.errorMessage.open('Please fill all blank fields and check error information', 'Err', {
        duration: 5000,
      });
    }
  }

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
