import { ExpenseService } from './../../services/expense.service';
import { Pair } from './../../models/pair';
import { Expense } from './../../models/expense';
import { Component, OnInit } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-expense-detail',
  templateUrl: './expense-detail.component.html',
  styleUrls: ['./expense-detail.component.scss']
})
export class ExpenseDetailComponent implements OnInit {

  isLoading: boolean;
  displayedColumns: string[];
  userId: number;
  expenseId: number;
  expenses: Expense[];
  expenseMonth: string[];  // recent 24 months
  expenseAmount: number;
  expenseTypeKey: number;
  expenseTypeValue: string;
  expenseTypes: Pair[];
  expenseDate: string;
  expenseNote: string;
  expenseSelectDate: string[];
  expenseSelectedDate: string;
  showAddExpense: boolean;
  showDeleteButton: boolean;

  constructor(private jwtHelper: JwtHelperService, private expenseService: ExpenseService, private errorMessage: MatSnackBar) {
    const token = jwtHelper.decodeToken(localStorage.getItem('access_token'));
    this.userId = token.aud;
    this.displayedColumns = ['amount', 'type', 'date', 'note', 'action'];
    this.expenseMonth = ['All'];
    this.expenseSelectedDate = 'All';
    this.expenseTypes = [
      { key: 0, value: 'Housing'},
      { key: 1, value: 'Transportation'},
      { key: 2, value: 'Food'},
      { key: 3, value: 'Utilities'},
      { key: 4, value: 'Clothing'},
      { key: 5, value: 'Healthcare'},
      { key: 6, value: 'Insurance'},
      { key: 7, value: 'Debt'},
      { key: 8, value: 'Education'},
      { key: 9, value: 'Entertainment'},
      { key: 10, value: 'Other'}
    ];
    this.showAddExpense = false;
    this.showDeleteButton = false;

    const today = new Date();
    // add recent 24 months into list
    let aMonth = today.getMonth();
    let aYear = today.getFullYear();
    for (let i = 0; i < 24; i++) {
      // add 0 if month is (1 ~ 9), eg 2020-01, 2020-02...
      this.expenseMonth.push(aYear + (aMonth < 9 ? '-0' : '-') + (aMonth + 1));
      aMonth--;
      if (aMonth < 0) {
          aMonth = 11;
          aYear--;
      }
    }
  }

  ngOnInit(): void {
    this.getExpenses();
  }

  public edit(event: Expense): void {
    this.showDeleteButton = true;
    this.showAddExpense = true;
    this.expenseId = event.expenseId;
    this.expenseAmount = event.amount;
    this.expenseDate = event.date;
    this.expenseNote = event.note;
    // map the selection list
    this.expenseTypes.forEach(type => {
      if (type.value === event.type) {
        this.expenseTypeKey = type.key;
        return;
      }
    });
  }

  public add(): void {
    this.showAddExpense = true;
  }

  public save(): void {
    if (this.expenseId !== null && this.expenseId !== undefined) { // if id is not null, then the state is "edit an income"

    } else {  // if id is null or undefined, then the state is "add an income"

    }
    // initialize form state
    this.cancel();
  }

  public deleteExpense(): void {
    this.cancel();
  }

  public cancel(): void {
    this.expenseId = null;
    this.expenseAmount = null;
    this.expenseTypeKey = null;
    this.expenseTypeValue = null;
    this.expenseDate = null;
    this.expenseNote = null;
    this.showAddExpense = false;
    this.showDeleteButton = false;
  }

  public changeExpenseDate(): void {
    this.getExpenses();
  }

  public getExpenses(): void {
    this.isLoading = true;
    this.expenseService.getExpenses(this.userId, this.expenseSelectedDate).subscribe(resp => {
      this.expenses = resp.data;
      this.isLoading = false;
    }, err => {
      console.log(err);
      this.errorMessage.open(err.error.message, 'Err', {
        duration: 5000,
      });
      this.isLoading = false;
    });
  }
}
