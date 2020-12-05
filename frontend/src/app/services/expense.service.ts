import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseWrapper } from '../models/response-wrapper';
import { Expense } from '../models/expense';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private expenseUrl = 'http://localhost:8080/api/expense/';

  constructor(private http: HttpClient) { }

  public getExpenses(userId, date: string): Observable<ResponseWrapper<Expense[]>> {
    const params = { date };
    return this.http.get<ResponseWrapper<Expense[]>>(this.expenseUrl + userId, { params });
  }
}
