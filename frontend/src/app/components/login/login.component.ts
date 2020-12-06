import { ResponseWrapper } from './../../models/response-wrapper';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { Observable } from 'rxjs';
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


  constructor(private userService: UserService, private router: Router, private errorMessage: MatSnackBar) {
    this.isLoading = false;
    this.showErrMsg = false;
    this.validEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  }

  ngOnInit(): void {
  }

  public signIn(): void {
    if (!this.showErrMsg) {
      this.isLoading = true;
      this.passwordCheck().subscribe(resp => {
        localStorage.setItem('access_token', resp.data);
        this.router.navigateByUrl('main');
      }, err => {
        this.errorMessage.open('Invalid Email And/Or Password', 'Err', {
          duration: 3000,
        });
      });
      this.isLoading = false;
    } else {
      this.errorMessage.open('Invalid Email And/Or Password', 'Err', {
        duration: 3000,
      });
    }
  }

  private passwordCheck(): Observable<ResponseWrapper<string>> {
    const user: User = new User('', this.password, this.email, '');
    return this.userService.checkLogin(user);
  }

  public emailCheck(): void {
    if (!this.validEmail.test(this.email)) {
      this.showErrMsg = true;
    } else {
      this.showErrMsg = false;
    }
  }
}
