import { User } from './../models/user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8081/api/user/';

  constructor(private http: HttpClient) { }

  public createUser(user: User): Observable<User> {
    return this.http.post<User>(this.userUrl, user);
  }
}
