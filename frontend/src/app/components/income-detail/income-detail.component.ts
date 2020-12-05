import { IncomeService } from './../../services/income.service';
import { Component, OnInit } from '@angular/core';
import { Income } from 'src/app/models/income';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Pair } from '../../models/pair';

@Component({
  selector: 'app-income-detail',
  templateUrl: './income-detail.component.html',
  styleUrls: ['./income-detail.component.scss']
})

export class IncomeDetailComponent implements OnInit {

  userId: number; // current userId
  isLoading: boolean;
  incomeId: number; // the id of income which is currently edited
  incomes: Income[];
  incomeMonth: string[];  // recent 24 months
  incomeTypes: Pair[];
  incomeAmount: number;
  incomeTypeKey: number; // used together with mat-select and incomeTypes
  incomeTypeValue: string;
  incomeDate: string; // date of an income
  incomeNote: string;
  displayedColumns: string[]; // head column of tables
  incomeSelectedDate: string; // month selected, i.e. 2020-01-01, 2020-03-02
  showAddIncome: boolean;
  showDeleteButton: boolean;

  constructor(private jwtHelper: JwtHelperService, private incomeService: IncomeService, private errorMessage: MatSnackBar) {
    const token = jwtHelper.decodeToken(localStorage.getItem('access_token'));
    this.userId = token.aud;
    this.displayedColumns = ['amount', 'type', 'date', 'note', 'action'];
    this.incomeMonth = ['All'];
    this.incomeSelectedDate = 'All';
    this.incomeTypes = [
      { key: 0, value: 'Salary'},
      { key: 1, value: 'Investment'},
      { key: 2, value: 'Scholarship'},
      { key: 3, value: 'Other'}];
    this.showAddIncome = false;
    this.showDeleteButton = false;

    const today = new Date();
    // add recent 24 months into list
    let aMonth = today.getMonth();
    let aYear = today.getFullYear();
    for (let i = 0; i < 24; i++) {
      // add 0 if month is (1 ~ 9), eg 2020-01, 2020-02...
      this.incomeMonth.push(aYear + (aMonth < 9 ? '-0' : '-') + (aMonth + 1));
      aMonth--;
      if (aMonth < 0) {
          aMonth = 11;
          aYear--;
      }
    }
  }

  ngOnInit(): void {
    this.getIncomes();
  }

  public edit(event: Income): void {
    this.showDeleteButton = true;
    this.showAddIncome = true;
    this.incomeId = event.incomeId;
    this.incomeAmount = event.amount;
    this.incomeDate = event.date;
    this.incomeNote = event.note;
    // map the selection list
    this.incomeTypes.forEach(type => {
      if (type.value === event.type) {
        this.incomeTypeKey = type.key;
        return;
      }
    });
  }

  // show the add income window
  public add(): void {
    this.showAddIncome = true;
  }

  // save or update the income
  public save(): void {
    if (this.incomeId !== null && this.incomeId !== undefined) { // if id is not null, then the state is "edit an income"

    } else {  // if id is null or undefined, then the state is "add an income"

    }
    // initialize form state
    this.cancel();
  }

  public deleteIncome(): void {
    // initialize form state
    this.cancel();
  }

  public cancel(): void {
    this.incomeId = null;
    this.incomeAmount = null;
    this.incomeTypeKey = null;
    this.incomeTypeValue = null;
    this.incomeDate = null;
    this.incomeNote = null;
    this.showAddIncome = false;
    this.showDeleteButton = false;
  }

  public changeIncomeDate(): void {
    // after change the month, get income list again
    this.getIncomes();
  }

  public getIncomes(): void {
    this.isLoading = true;
    this.incomeService.getIncomes(this.userId, this.incomeSelectedDate).subscribe(resp => {
      this.incomes = resp.data;
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

