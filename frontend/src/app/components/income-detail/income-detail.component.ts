import { ResponseWrapper } from './../../models/response-wrapper';
import { ActivatedRoute, Router } from '@angular/router';
import { IncomeService } from './../../services/income.service';
import { Component, OnInit } from '@angular/core';
import { Income } from 'src/app/models/income';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';
import { generateMonth } from './../../utils/month-generate-util';

@Component({
  selector: 'app-income-detail',
  templateUrl: './income-detail.component.html',
  styleUrls: ['./income-detail.component.scss']
})

export class IncomeDetailComponent implements OnInit {

  userId: number; // current userId
  isLoading: boolean;
  income: Income;
  incomes: Income[];
  incomeMonth: string[];  // recent 24 months
  incomeTypes: string[];
  displayedColumns: string[]; // head column of tables
  incomeSelectedDate: string; // month selected, i.e. 2020-01-01, 2020-03-02
  showAddIncome: boolean;
  showDeleteButton: boolean;

  constructor(private jwtHelper: JwtHelperService,
              private incomeService: IncomeService,
              private errorMessage: MatSnackBar,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.income = new Income();
    const token = this.jwtHelper.decodeToken(localStorage.getItem('access_token'));
    this.userId = token.aud;
    this.displayedColumns = ['amount', 'type', 'date', 'note', 'action'];
    this.incomeTypes = ['Salary', 'Investment', 'Scholarship', 'Other'];
    this.showAddIncome = false;
    this.showDeleteButton = false;
    this.router.navigate(['.'], {relativeTo: this.activatedRoute, queryParams: {date: 'All'}});
    this.incomeMonth = [];
    generateMonth(this.incomeMonth);
    this.incomeSelectedDate = 'All';
  }

  ngOnInit(): void {
    this.getIncomes();
  }

  public edit(event: Income): void {
    this.showDeleteButton = true;
    this.showAddIncome = true;
    this.income = JSON.parse(JSON.stringify(event)); // deep copy
  }

  // show the add income window
  public add(): void {
    this.showAddIncome = true;
  }

  // save or update the income
  public save(): void {
    if (!this.checkFormField()) {
      this.errorMessage.open('Please fill all blank fields and check error information', 'Err', {
        duration: 5000,
      });
    } else {
      this.isLoading = true;
      if (this.income.id !== null && this.income.id !== undefined) { // if id is not null, then the state is "edit an income"
          this.incomeService.updateIncome(this.income.id, this.income).subscribe(resp => {
          this.successRespHandling(resp);
        }, err => {
          this.errorHandling(err);
        });
      } else {  // if id is null or undefined, then the state is "add an income"
        this.incomeService.addIncome(this.userId, this.income).subscribe(resp => {
          this.successRespHandling(resp);
        }, err => {
          this.errorHandling(err);
        });
      }
    }
  }

  public deleteIncome(): void {
    this.isLoading = true;
    this.incomeService.deleteIncome(this.income.id).subscribe(resp => {
      this.successRespHandling(resp);
    }, err => {
      this.errorHandling(err);
    });
  }

  // initialize form state
  public cancel(): void {
    this.isLoading = false;
    this.income = new Income();
    this.showAddIncome = false;
    this.showDeleteButton = false;
  }

  public changeIncomeDate(): void {
    // after change the month, get income list again
    this.router.navigate(['.'], {relativeTo: this.activatedRoute, queryParams: {date: this.incomeSelectedDate}});
    this.getIncomes();
  }

  // get incomes by month
  public getIncomes(): void {
    this.isLoading = true;
    this.incomeService.getIncomes(this.userId, this.incomeSelectedDate).subscribe(resp => {
      this.incomes = resp.data;
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
    alert(resp.message);
    this.cancel();
    location.reload();
  }

  public checkFormField(): boolean {
    return this.income.amount !== null && this.income.amount !== undefined
        && this.income.date !== null && this.income.date !== undefined
        && this.income.type !== null && this.income.type !== undefined;
  }
}
