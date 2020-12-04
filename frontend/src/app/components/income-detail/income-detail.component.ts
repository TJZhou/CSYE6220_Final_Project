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
    this.incomeDate = ['ALL'];
    const today = new Date();
    // add recent 24 months into list
    let aMonth = today.getMonth();
    let aYear = today.getFullYear();
    for (let i = 0; i < 24; i++) {
        // add 0 if month is (1 ~ 9), eg 2020-01, 2020-02...
        this.incomeDate.push(aYear + (aMonth < 9 ? '-0' : '-') + (aMonth + 1));
        aMonth--;
        if (aMonth < 0) {
            aMonth = 11;
            aYear--;
        }
    }
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
