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
    this.expenseDate = ['ALL', '2020-10', '2020-11', '2020-12'];
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
