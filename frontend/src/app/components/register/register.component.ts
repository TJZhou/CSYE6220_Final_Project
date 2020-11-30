import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from '../../models/user';


@Component({
  selector: 'app-login',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  phone: string;
  isLoading: boolean;
  validPassword: RegExp;
  validEmail: RegExp;
  validPhone: RegExp;
  validUsername: RegExp;
  showErrMsgUsername: boolean;
  showErrMsgEmail: boolean;
  showErrMsgPassword: boolean;
  showErrMsgConfirmPassword: boolean;
  showErrMsgPhone: boolean;

  constructor(private router: Router, private errorMessage: MatSnackBar) {
    this.isLoading = false;
    this.showErrMsgUsername = false;
    this.showErrMsgEmail = false;
    this.showErrMsgPassword = false;
    this.showErrMsgConfirmPassword = false;
    this.showErrMsgPhone = false;
    this.validUsername = /^[a-zA-Z0-9]+([_\.-]?[a-zA-Z0-9])*$/;
    this.validEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    this.validPhone = /^(\+\d{1,2}\s?)?1?\-?\.?\s?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/;
    this.validPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  }

  ngOnInit(): void {
  }

  public register(): void {
    if (this.emailCheck() && this.usernameCheck() && this.passwordCheck() && this.confirmPasswordCheck() && this.phoneCheck()) {
      const user: User = new User(this.username, this.password, this.email, this.phone);
      this.router.navigateByUrl('login');
    } else {
      this.errorMessage.open('Please fill all blank fields and check error information', 'Err', {
        duration: 5000,
      });
    }
  }

  public usernameCheck(): boolean {
    // if it's not valid (validate function returns false), then showErrMsgUsername should be true
    this.showErrMsgUsername = !this.validate(this.username, this.validUsername);
    return !this.showErrMsgUsername;
  }

  public emailCheck(): boolean {
    this.showErrMsgEmail = !this.validate(this.email, this.validEmail);
    return !this.showErrMsgEmail;
  }

  public phoneCheck(): boolean {
    this.showErrMsgPhone = !this.validate(this.phone, this.validPhone);
    return !this.showErrMsgPhone;
  }

  public passwordCheck(): boolean {
    this.showErrMsgPassword = !this.validate(this.password, this.validPassword);
    return !this.showErrMsgPassword;
  }

  public confirmPasswordCheck(): boolean {
    if (this.confirmPassword === undefined || this.confirmPassword !== this.password) {
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
