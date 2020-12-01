import { AuthGuardService } from './services/auth-guard.service';
import { AccountComponent } from './components/account/account.component';
import { BillComponent } from './components/bill/bill.component';
import { MainComponent } from './components/main/main.component';
import { ErrorComponent } from './components/error/error.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExpenseDetailComponent } from './components/expense-detail/expense-detail.component';
import { IncomeDetailComponent } from './components/income-detail/income-detail.component';

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'account', component: AccountComponent, canActivate: [AuthGuardService]},
  {path: 'bill', component: BillComponent, canActivate: [AuthGuardService]},
  {path: 'register', component: RegisterComponent, canActivate: [AuthGuardService]},
  {path: 'login', component: LoginComponent},
  {path: 'main', component: MainComponent, canActivate: [AuthGuardService]},
  {path: 'income', component: IncomeDetailComponent, canActivate: [AuthGuardService]},
  {path: 'expense', component: ExpenseDetailComponent, canActivate: [AuthGuardService]},
  {path: '**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
