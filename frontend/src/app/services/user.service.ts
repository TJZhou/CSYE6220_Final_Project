import { ResponseWrapper } from './../models/response-wrapper';
import { User } from './../models/user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/api/user/';

  constructor(private http: HttpClient) { }

  public createUser(user: User): Observable<ResponseWrapper<User>> {
    return this.http.post<ResponseWrapper<User>>(this.userUrl, user);
  }

  public getUser(id: number): Observable<ResponseWrapper<User>> {
    return this.http.get<ResponseWrapper<User>>(this.userUrl + id);
  }

  public checkLogin(user: User): Observable<ResponseWrapper<string>> {
    return this.http.post<ResponseWrapper<string>>(this.userUrl + 'login', user);
  }

  public updateUserInfo(user: User): Observable<ResponseWrapper<User>> {
    return this.http.put<ResponseWrapper<User>>(this.userUrl + user.userId, user);
  }

  public updateUserPassword(id: number, oldPassword: string, newPassword: string): Observable<ResponseWrapper<User>> {
    const pojo: object = {
      oldPassword,
      newPassword
    };
    return this.http.put<ResponseWrapper<User>>(this.userUrl + 'pswd/' + id, pojo);
  }
}
