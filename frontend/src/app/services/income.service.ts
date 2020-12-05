import { ResponseWrapper } from './../models/response-wrapper';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Income } from '../models/income';

@Injectable({
  providedIn: 'root'
})
export class IncomeService {

  private incomeUrl = 'http://localhost:8080/api/income/';

  constructor(private http: HttpClient) { }

  public getIncomes(userId: number, date: string): Observable<ResponseWrapper<Income[]>> {
    const params = { date };
    return this.http.get<ResponseWrapper<Income[]>>(this.incomeUrl + userId, { params });
  }
}
