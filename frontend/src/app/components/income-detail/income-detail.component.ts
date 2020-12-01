import { incomes as mockData } from './../../mock-data';
import { Component, OnInit } from '@angular/core';
import { Income } from 'src/app/models/income';

@Component({
  selector: 'app-income-detail',
  templateUrl: './income-detail.component.html',
  styleUrls: ['./income-detail.component.scss']
})
export class IncomeDetailComponent implements OnInit {

  incomes: Income[];
  incomeDate: string[];
  displayedColumns: string[];
  incomeSelectedDate: string;

  constructor() {
    this.incomes = mockData;
    this.displayedColumns = ['amount', 'type', 'date', 'note', 'action'];
    this.incomeDate = ['ALL', '2020-10', '2020-11', '2020-12'];
    this.incomeSelectedDate = 'ALL';
  }

  ngOnInit(): void {
  }

  public edit(event): void {
    console.log(event);
  }

  public add(): void {

  }

  public changeIncomeDate(eventDate): void {
    console.log(eventDate);
  }
}
