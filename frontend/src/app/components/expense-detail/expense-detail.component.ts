import { Expense } from './../../models/expense';
import { expenses as mockData} from './../../mock-data';
import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-expense-detail',
  templateUrl: './expense-detail.component.html',
  styleUrls: ['./expense-detail.component.scss']
})
export class ExpenseDetailComponent implements OnInit {

  expenses: Expense[];
  displayedColumns: string[];
  expenseDate: string[];
  expenseSelectedDate: string;

  constructor() {
    this.expenses = mockData;
    this.displayedColumns = ['amount', 'type', 'date', 'note', 'action'];
    this.expenseDate = ['ALL'];
    const today = new Date();
    // add recent 24 months into list
    let aMonth = today.getMonth();
    let aYear = today.getFullYear();
    for (let i = 0; i < 24; i++) {
        // add 0 if month is (1 ~ 9), eg 2020-01, 2020-02...
        this.expenseDate.push(aYear + (aMonth < 9 ? '-0' : '-') + (aMonth + 1));
        aMonth--;
        if (aMonth < 0) {
            aMonth = 11;
            aYear--;
        }
    }
    this.expenseSelectedDate = 'ALL';
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
  }}
