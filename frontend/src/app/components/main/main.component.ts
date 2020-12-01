import { mockIncome, mockExpense } from './../../mock-data';
import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent implements OnInit {
  incomeSelectedDate: string;
  expenseSelectedDate: string;

  Highcharts: typeof Highcharts = Highcharts;
  /**
   * @type {Highcharts.Options}
   * @memberof MainComponent
   */
  chartOptions: Highcharts.Options = {
    chart: {
      plotBackgroundColor: null,
      plotBorderWidth: null,
      plotShadow: false,
      type: 'pie',
      // height: 1000,
      // width: 1000
    },
    title: {
      text: 'Incomes',
    },
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
    series: mockIncome as Highcharts.SeriesOptionsType[],
  };

  incomeDate: string[];
  expenseDate: string[];

  constructor() {
    this.incomeDate = ['ALL', '2020-11', '2020-10', '2020-09'];
    this.expenseDate = ['ALL', '2020-11', '2020-10', '2020-09'];
    this.incomeSelectedDate = 'ALL';
    this.expenseSelectedDate = 'ALL';
  }

  ngOnInit(): void {}

  public changeIncomeDate(eventDate): void {
    console.log(eventDate);
  }

  public changeExpenseDate(eventDate): void {
    console.log(eventDate);
  }
}
