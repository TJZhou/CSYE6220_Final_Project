import { ResponseWrapper } from './../../models/response-wrapper';
import { ActivatedRoute, Router } from '@angular/router';
import { ExpenseService } from './../../services/expense.service';
import { Expense } from './../../models/expense';
import { Component, OnInit } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';
import { generateMonth } from './../../utils/month-generate-util';

@Component({
  selector: 'app-expense-detail',
  templateUrl: './expense-detail.component.html',
  styleUrls: ['./expense-detail.component.scss']
})

export class ExpenseDetailComponent implements OnInit {

  userId: number;
  isLoading: boolean;
  expense: Expense;
  expenses: Expense[];
  expenseMonth: string[];  // recent 24 months
  expenseTypes: string[];
  displayedColumns: string[];
  expenseSelectedDate: string;
  showAddExpense: boolean;
  showDeleteButton: boolean;

  constructor(private jwtHelper: JwtHelperService,
              private expenseService: ExpenseService,
              private errorMessage: MatSnackBar,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.expense = new Expense();
    const token = this.jwtHelper.decodeToken(localStorage.getItem('access_token'));
    this.userId = token.aud;
    this.displayedColumns = ['amount', 'type', 'date', 'note', 'action'];
    this.expenseTypes = ['Housing', 'Transportation', 'Food', 'Utilities', 'Clothing',
     'Healthcare', 'Insurance', 'Debt', 'Education', 'Entertainment', 'Other'];
    this.showAddExpense = false;
    this.showDeleteButton = false;
    this.router.navigate(['.'], {relativeTo: this.activatedRoute, queryParams: {date: 'All'}});
    this.expenseMonth = [];
    generateMonth(this.expenseMonth);
    this.expenseSelectedDate = 'All';
  }

  ngOnInit(): void {
    this.getExpenses();
  }

  public edit(event: Expense): void {
    this.showDeleteButton = true;
    this.showAddExpense = true;
    this.expense = JSON.parse(JSON.stringify(event));
  }

  public add(): void {
    this.showAddExpense = true;
  }

  // save or update the expense
  public save(): void {
    if (!this.checkFormField()) {
      this.errorMessage.open('Please fill all blank fields and check error information', 'Err', {
        duration: 5000,
      });
    } else {
      this.isLoading = true;
      if (this.expense.id !== null && this.expense.id !== undefined) { // if id is not null, then the state is "edit an income"
          this.expenseService.updateExpense(this.expense.id, this.expense).subscribe(resp => {
          this.successRespHandling(resp);
        }, err => {
          this.errorHandling(err);
        });
      } else {  // if id is null or undefined, then the state is "add an income"
        this.expenseService.addExpense(this.userId, this.expense).subscribe(resp => {
          this.successRespHandling(resp);
        }, err => {
          this.errorHandling(err);
        });
      }
    }
  }

  public deleteExpense(): void {
    this.isLoading = true;
    this.expenseService.deleteExpense(this.expense.id).subscribe(resp => {
      this.successRespHandling(resp);
    }, err => {
      this.errorHandling(err);
    });
  }

  // initialize form state
  public cancel(): void {
    this.isLoading = false;
    this.expense = new Expense();
    this.showAddExpense = false;
    this.showDeleteButton = false;
  }


  public changeExpenseDate(): void {
    this.router.navigate(['.'], {relativeTo: this.activatedRoute, queryParams: {date: this.expenseSelectedDate}});
    this.getExpenses();
  }


  public getExpenses(): void {
    this.isLoading = true;
    this.expenseService.getExpenses(this.userId, this.expenseSelectedDate).subscribe(resp => {
      this.expenses = resp.data;
      this.isLoading = false;
    }, err => {
      this.errorHandling(err);
    });
  }

  public errorHandling(err): void {
    console.log(err);
    this.errorMessage.open(err.error.message, 'Err', {
      duration: 5000,
    });
    this.isLoading = false;
    this.cancel();
  }

  public successRespHandling(resp: ResponseWrapper<any>): void {
    // alert(resp.message);
    this.cancel();
    location.reload();
  }

  public checkFormField(): boolean {
    return this.expense.amount !== null && this.expense.amount !== undefined
        && this.expense.date !== null && this.expense.date !== undefined
        && this.expense.type !== null && this.expense.type !== undefined;
  }
}
