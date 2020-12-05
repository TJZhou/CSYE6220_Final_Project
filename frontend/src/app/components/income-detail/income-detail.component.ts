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

  userId: number;
  isLoading: boolean;
  incomes: Income[];
  incomeMonth: string[];
  incomeTypes: Pair[];
  incomeAmount: number;
  incomeType: string;
  incomeDate: string;
  incomeNote: string;
  displayedColumns: string[];
  incomeSelectedDate: string;
  showAddIncome: boolean;
  showUpdateIncome: boolean;

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
    this.showUpdateIncome = false;

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

  public edit(event): void {
    console.log(event);
    this.showUpdateIncome = true;
  }

  // show the add income window
  public add(): void {
    this.showAddIncome = true;
  }

  // save the income
  public save(): void {
    console.log(this.incomeAmount, this.incomeTypes[this.incomeType], this.incomeDate, this.incomeNote);
    this.showAddIncome = false;
  }

  public cancel(): void {
    this.showAddIncome = false;
    this.showUpdateIncome = false;
    this.incomeAmount = null;
    this.incomeType = null;
    this.incomeDate = null;
    this.incomeNote = null;
  }

  public changeIncomeDate(eventDate): void {
    // console.log(eventDate);
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

