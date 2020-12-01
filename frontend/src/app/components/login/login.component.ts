import { users, mockToken } from './../../mock-data';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: string;
  password: string;
  isLoading: boolean;
  validEmail: RegExp;
  showErrMsg: boolean;


  constructor(private router: Router, private errorMessage: MatSnackBar) {
    this.isLoading = false;
    this.showErrMsg = false;
    this.validEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  }

  ngOnInit(): void {
  }

  public signIn(): void {
    if (!this.showErrMsg && this.passwordCheck()) {
      localStorage.setItem('access_token', mockToken);
      this.router.navigateByUrl('main');
    } else {
      this.errorMessage.open('Invalid Email And/Or Password', 'Err', {
        duration: 3000,
      });
    }
  }

  private passwordCheck(): boolean {
    return users.some(user =>
      user.email === this.email && user.password === this.password
    );
  }

  public emailCheck(): void {
    if (!this.validEmail.test(this.email)) {
      this.showErrMsg = true;
    } else {
      this.showErrMsg = false;
    }
  }
}
