import { Expense } from '../models/expense';
import { Income } from '../models/income';
import { User } from '../models/user';
import * as Highcharts from 'highcharts';

export const users: User[] = [
  {
    userId: 1,
    username: 'Tianju Zhou',
    password: '123456',
    email: 'zhoutianju17@gmail.com',
    phone: '1234567890',
  },
  {
    userId: 2,
    username: 'Sheng Xiang',
    password: '123456',
    email: 'xiangsheng@gmail.com',
    phone: '1234567890',
  },
  {
    userId: 3,
    username: 'Yu Chen',
    password: '123456',
    email: 'chenyu@gmail.com',
    phone: '1234567890',
  },
];

export const incomes: Income[] = [
  {
    id: 1,
    userId: 1,
    amount: 1000,
    type: 'salary',
    date: '2020-09-01',
    note: '',
  },
  {
    id: 2,
    userId: 1,
    amount: 1000,
    type: 'salary',
    date: '2020-10-01',
    note: '',
  },
  {
    id: 3,
    userId: 1,
    amount: 1000,
    type: 'scholarship',
    date: '2020-11-01',
    note: 'scholarship from College Of Engineering',
  },
];

export const expenses: Expense[] = [
  {
    id: 1,
    userId: 1,
    amount: 1000,
    type: 'housing',
    date: '2020-09-01',
    note: 'rent fee',
  },
  {
    id: 2,
    userId: 1,
    amount: 1000,
    type: 'transportation',
    date: '2020-10-01',
    note: 'metro monthly pass',
  },
  {
    id: 3,
    userId: 1,
    amount: 1000,
    type: 'education',
    date: '2020-11-01',
    note: 'tuition',
  },
];

export const mockToken =
  'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwibmFtZSI6IlRpYW5qdSBaaG91IiwiaWF0IjoxNjE2MjM5MDIyLCJlbWFpbCI6Inpob3V0aWFuanUxN0BnbWFpbC5jb20iLCJwaG9uZSI6IjEyMzQ1Njc4OTAifQ.5SHrdYxC4Txdt08RlP7AEWa2AmnVONwvMfFK9Ez6bW8';

export const mockIncome: object[] = [
  {
    type: 'pie',
    name: 'Browser share',
    data: [
      { name: 'salary', y: 45.0, sliced: true, selected: true },
      ['stock', 26.8],
      ['investment', 12.8],
      ['other', 15.2],
    ],
  },
];

export const mockExpense: Highcharts.SeriesOptionsType[] = [
  {
    type: 'pie',
    name: 'Browser share',
    data: [
      {name: 'housing',
      y: 23.0,
      sliced: true,
      selected: true
    },
      ['education', 24.0],
      ['transportation', 10.0],
      ['food', 12.0],
      ['utility', 8.0],
      ['clothing', 8.0],
      ['other', 15.0]
    ],
  },
];
