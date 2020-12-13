import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseWrapper } from '../models/response-wrapper';
import { Expense } from '../models/expense';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private expenseUrl = environment.apiUrl + '/expense/';

  constructor(private http: HttpClient) { }

  public getExpenses(userId, date: string): Observable<ResponseWrapper<Expense[]>> {
    const params = { date };
    return this.http.get<ResponseWrapper<Expense[]>>(this.expenseUrl + userId, { params });
  }

  public getAndGroupExpenses(userId, date: string): Observable<ResponseWrapper<Map<string, object>[]>> {
    const params = { date };
    return this.http.get<ResponseWrapper<Map<string, object>[]>>(this.expenseUrl + 'overview/' + userId, { params });
  }

  public addExpense(userId: number, expense: Expense): Observable<ResponseWrapper<number>> {
    return this.http.post<ResponseWrapper<number>>(this.expenseUrl + userId, expense);
  }

  public updateExpense(userId: number, expense: Expense): Observable<ResponseWrapper<Expense>> {
    return this.http.put<ResponseWrapper<Expense>>(this.expenseUrl + userId, expense);
  }

  public deleteExpense(expenseId: number): Observable<ResponseWrapper<void>> {
    return this.http.delete<ResponseWrapper<void>>(this.expenseUrl + expenseId);
  }
}
