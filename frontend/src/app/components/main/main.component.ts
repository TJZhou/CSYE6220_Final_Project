import { Expense } from './../../models/expense';
import { ExpenseService } from './../../services/expense.service';
import { IncomeService } from './../../services/income.service';
import { mockIncome, mockExpense } from './../../utils/mock-data';
import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';
import { generateMonth } from './../../utils/month-generate-util';
import { Income } from 'src/app/models/income';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent implements OnInit {
  token: string;
  userId: number;
  isLoading: boolean;
  incomeSelectedDate: string;
  expenseSelectedDate: string;
  incomeDate: string[];
  expenseDate: string[];
  incomesGroup: Map<string, Income[]>;
  expenseGroup: Map<string, Expense[]>;
  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {
    chart: {
      plotBackgroundColor: null,
      plotBorderWidth: null,
      plotShadow: false,
      type: 'pie',
      // height: 1000,
      // width: 1000
    },
    // title: {
    //   text: 'Incomes',
    // },
    tooltip: {
      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>',
    },
    accessibility: {
      point: {
        valueSuffix: '%',
      },
    },
    plotOptions: {
      pie: {
        allowPointSelect: true,
        cursor: 'pointer',
        dataLabels: {
          enabled: true,
          format: '<b>{point.name}</b>: {point.percentage:.1f} %',
        },
      },
    },
    // series: mockIncome as Highcharts.SeriesOptionsType[],
  };
  incomesChartOption: Highcharts.Options;
  expensesChartOption: Highcharts.Options;

  constructor(private jwtHelper: JwtHelperService,
              private incomeService: IncomeService,
              private expenseService: ExpenseService,
              private errorMessage: MatSnackBar) {
    const token = this.jwtHelper.decodeToken(localStorage.getItem('access_token'));
    this.userId = token.aud;
    this.isLoading = false;
    this.incomeDate = [];
    this.expenseDate = [];
    generateMonth(this.incomeDate);
    generateMonth(this.expenseDate);
    this.incomeSelectedDate = 'All';
    this.expenseSelectedDate = 'All';
    this.incomesChartOption = JSON.parse(JSON.stringify(this.chartOptions)); // deep copy
    this.expensesChartOption = JSON.parse(JSON.stringify(this.chartOptions));
    this.incomesChartOption.title = {text: 'Incomes'};
    this.expensesChartOption.title = {text: 'Expenses'};
  }

  ngOnInit(): void {
    this.expenseService.getAndGroupExpenses(this.userId, this.expenseSelectedDate).subscribe(resp => {
      this.expenseGroup = resp.data;
    }, err => {
      this.errorHandling(err);
    });
  }

  public changeIncomeDate(eventDate): void {
  }

  public changeExpenseDate(eventDate): void {
  }

  private errorHandling(err): void {
    console.log(err);
    this.errorMessage.open(err.error.message, 'Err', {
      duration: 5000,
    });
    this.isLoading = false;
  }
}
