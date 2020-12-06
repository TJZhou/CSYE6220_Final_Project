import { ResponseWrapper } from './../../models/response-wrapper';
import { Expense } from './../../models/expense';
import { ExpenseService } from './../../services/expense.service';
import { IncomeService } from './../../services/income.service';
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
    this.incomesChartOption = this.generateChart(null, 'Incomes');
    this.expensesChartOption = this.generateChart(null, 'Expense');
  }

  ngOnInit(): void {
    this.getAndGroupIncome();
    this.getAndGroupExpense();
  }

  public changeIncomeDate(): void {
    this.getAndGroupIncome();
  }

  public changeExpenseDate(): void {
    this.getAndGroupExpense();
  }

  private errorHandling(err): void {
    console.log(err);
    this.errorMessage.open(err.error.message, 'Err', {
      duration: 5000,
    });
    this.isLoading = false;
  }

  private getAndGroupIncome(): void {
    this.isLoading = true;
    this.incomeService.getAndGroupIncomes(this.userId, this.incomeSelectedDate).subscribe(resp => {
      this.incomesChartOption = this.successRespHandling('Income', resp);
    }, err => {
      this.errorHandling(err);
    });
  }

  private getAndGroupExpense(): void {
    this.isLoading = true;
    this.expenseService.getAndGroupExpenses(this.userId, this.expenseSelectedDate).subscribe(resp => {
      this.expensesChartOption = this.successRespHandling('Expense', resp);
    }, err => {
      this.errorHandling(err);
    });
  }

  private successRespHandling(title: string, resp: ResponseWrapper<Map<string, object>[]>): Highcharts.Options {
    const formatData = [];
    resp.data.forEach(entry => {
      // tslint:disable-next-line:no-string-literal
      formatData.push([entry['type'], entry['sum']]);
    });
    this.isLoading = false;
    return this.generateChart(formatData, title);
  }

  private generateChart(myData: Array<[string, number]>, title: string): Highcharts.Options {
    const chartOptions: Highcharts.Options = {
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie',
      },
      title: {
        text: title
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.y}, {point.percentage:.1f}%</b>',
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
            format: '<b>{point.name}</b>: {point.y}, {point.percentage:.1f} %',
          },
        },
      },
      series: [{
          type: 'pie',
          name: title,
          data: myData
        },
      ]
    };
    return chartOptions;
  }
}
